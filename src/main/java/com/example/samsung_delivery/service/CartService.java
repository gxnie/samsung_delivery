package com.example.samsung_delivery.service;

import com.example.samsung_delivery.config.CartCookieEncoder;
import com.example.samsung_delivery.dto.cart.CartDto;
import com.example.samsung_delivery.dto.cart.CartItemDto;
import com.example.samsung_delivery.dto.order.OrderResponseDto;
import com.example.samsung_delivery.entity.Menu;
import com.example.samsung_delivery.entity.Order;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.OrderStatus;
import com.example.samsung_delivery.error.errorcode.ErrorCode;
import com.example.samsung_delivery.error.exception.CustomException;
import com.example.samsung_delivery.repository.MenuRepository;
import com.example.samsung_delivery.repository.OrderRepository;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartCookieEncoder cartCookieEncoder;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    // 장바구니 조회 : 쿠키에서 장바구니 데이터를 가져와 반환
    public CartDto getCart(HttpServletRequest request) {
        List<CartItemDto> items = cartCookieEncoder.getCartFromCookies(request);
        CartDto cartDto = new CartDto();
        cartDto.setItems(items);

        // 만료 여부 확인 : 장바구니 생성 이후 1일이 지나면 True 를 반환 -> 예외 처리
        if (cartDto.isExpired()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "장바구니가 만료되었습니다.");
        }

        return cartDto;
    }

    // 장바구니 저장 : 새 데이터를 쿠키에 저장하고 반환
    public CartDto saveCart(HttpServletResponse response, CartDto cartDto) {
        validateCart(cartDto);

        // 장바구니 내역 총합 계산
        int totalPrice = cartDto.calculateTotalPrice();
        // 디버깅용 출력
        System.out.println("장바구니 총합: " + totalPrice);

        cartDto.setLastUpdated(LocalDateTime.now());
        cartCookieEncoder.setCartToCookies(cartDto.getItems(), response);

        return cartDto;
    }

    // 장바구니 수정 : 기존 장바구니 데이터를 새 데이터로 덮어쓰기
    public CartDto updateCart(HttpServletRequest request, HttpServletResponse response, CartDto newCart) {
        validateCart(newCart);

        // 장바구니 데이터를 쿠키에 저장
        newCart.setLastUpdated(LocalDateTime.now());
        cartCookieEncoder.setCartToCookies(newCart.getItems(), response);

        return newCart;
    }

    // 장바구니에 메뉴 추가 : 새로운 상품 추가 또는 기존 상품 수량 증가
    public CartDto addToCart(HttpServletRequest request, HttpServletResponse response, CartItemDto newItem) {
        List<CartItemDto> cartItems = cartCookieEncoder.getCartFromCookies(request);

        // 동일한 메뉴가 이미 장바구니에 있으면 수량 증가, 없으면 추가
        Optional<CartItemDto> existingItem = cartItems.stream()
                .filter(item -> item.getMenuId().equals(newItem.getMenuId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItemDto item = existingItem.get();
            item.setQuantity(item.getQuantity() + newItem.getQuantity());
        } else {
            cartItems.add(newItem);
        }

        // 새 데이터로 장바구니 유효성 검증
        CartDto tempCart = new CartDto();
        tempCart.setItems(cartItems);
        tempCart.setStoreId(newItem.getStoreId());
        validateCart(tempCart);

        cartCookieEncoder.setCartToCookies(cartItems, response);

        // 업데이트된 장바구니 쿠키 저장
        CartDto updatedCart = new CartDto();
        updatedCart.setItems(cartItems);
        updatedCart.setLastUpdated(LocalDateTime.now());
        return updatedCart;
    }

    // 장바구니에서 주문 생성 : 장바구니 데이터를 기반으로 주문을 생성하고 장바구니 초기화
    @Transactional
    public List<OrderResponseDto> createOrdersFromCart(Long userId, HttpServletRequest request, HttpServletResponse response) {
        List<CartItemDto> cartItems = cartCookieEncoder.getCartFromCookies(request);

        if (cartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "장바구니가 비어 있습니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        List<OrderResponseDto> orderResponses = new ArrayList<>();

        for (CartItemDto item : cartItems) {
            Menu menu = menuRepository.findById(item.getMenuId()).orElseThrow(
                    () -> new CustomException(ErrorCode.MENU_NOT_FOUND)
            );

            // 주문 번호 생성
            String orderNumber = UUID.randomUUID().toString();

            // 주문 생성
            Order order = new Order(item.getQuantity(), item.getUsePoint(), menu.getPrice() * item.getQuantity(), item.getAddress(), orderNumber);
            order.setUser(user);
            order.setMenu(menu);
            order.setStatus(OrderStatus.ORDER_COMPLETED);

            orderRepository.save(order);
            orderResponses.add(new OrderResponseDto(order));
        }

        // 주문 완료 후 장바구니 초기화
        clearCart(response);

        return orderResponses;
    }

    // 장바구니 초기화 : 쿠키에서 장바구니 데이터 삭제
    public void clearCart(HttpServletResponse response) {
        cartCookieEncoder.deleteCartCookie(response);
    }

    // 장바구니 유효성 검증 : 비어 있는지, 동일한 가게의 상품만 담겼는지 확인
    private void validateCart(CartDto cartDto) {
        if (cartDto.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "장바구니가 비어 있습니다.");
        }

        Long storeId = null;
        for (CartItemDto item : cartDto.getItems()) {
            if (storeId == null) {
                storeId = cartDto.getStoreId();
            } else if (!storeId.equals(cartDto.getStoreId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "장바구니에는 같은 가게의 상품만 담을 수 있습니다.");
            }
        }
    }
}
