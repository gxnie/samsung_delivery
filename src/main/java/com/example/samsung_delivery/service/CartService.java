package com.example.samsung_delivery.service;

import com.example.samsung_delivery.config.CartCookieEncoder;
import com.example.samsung_delivery.dto.cart.CartDto;
import com.example.samsung_delivery.dto.cart.CartItemDto;
import com.example.samsung_delivery.entity.Cart;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.repository.CartRepository;
import com.example.samsung_delivery.repository.StoreRepository;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    @Transactional
    public List<CartDto> getCartList(String cartCookie) {
        if (cartCookie == null) {
            return new ArrayList<>();
        }

        List<CartDto> cartList = (List<CartDto>) CartCookieEncoder.decode(cartCookie);

        // 만료된 장바구니는 제거
        return cartList.stream()
                .filter(cart -> !cart.isExpired())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CartDto> addToCart(String cartCookie, Long userId, Long storeId, CartItemDto newItem) {
        List<CartDto> cartList = getCartList(cartCookie);

        CartDto existingCart = cartList.stream()
                .filter(cart -> cart.getStoreId().equals(storeId))
                .findFirst()
                .orElse(null);

        if (existingCart == null) {
            // 새로운 가게 장바구니 생성
            CartDto newCart = new CartDto(userId, storeId, new ArrayList<>(), LocalDateTime.now());
            newCart.getItems().add(newItem);
            cartList.add(newCart);
        } else {
            // 기존 가게의 장바구니 업데이트
            existingCart.getItems().add(newItem);
            existingCart.setLastUpdated(LocalDateTime.now());
        }

        return cartList;
    }

    public void setCartCookie(HttpServletResponse response, List<CartDto> cartList) {
        Cookie cookie = new Cookie("cart", CartCookieEncoder.encode(cartList));
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24 * 60 * 60); // 1일
        response.addCookie(cookie);
    }

    public void clearCartCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("cart", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }



//    // 하루가 지난 장바구니는 조회되지 않도록 검증
//    @Transactional
//    public List<CartResponseDto> getCart(Long userId) {
//        List<Cart> cartList = cartRepository.findByUserId(userId).stream()
//                // 하루가 지난 항목 체크
//                .filter(cart -> !isCartExpired(cart.getCreatedAt()))
//                .toList();
//
//        return cartList.stream()
//                .map(cart -> new CartResponseDto(cart.getId(), cart.getMenuName(), cart.getPrice(), cart.getQuantity()))
//                .toList();
//    }
//
//    // 장바구니가 하루가 지났는지 확인
//    private boolean isCartExpired(LocalDateTime createdAt) {
//        return createdAt.isBefore(LocalDateTime.now().minusDays(1));
//    }
//
//    @Transactional
//    public List<Cart> addToCart(Long userId, Long storeId, String menuName, int quantity, int price) {
//        // DB에서 userId 기준으로 장바구니 조회
//        List<Cart> cartList = cartRepository.findByUserId(userId);
//
//        if (cartList.isEmpty()) {
//            // 장바구니가 없으면 새로운 장바구니 생성
//            User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
//            Store store = storeRepository.findById(storeId).orElseThrow(EntityNotFoundException::new);
//
//            Cart cart = new Cart(user, store, menuName, quantity, price);
//
//            Cart savedCart = cartRepository.save(cart);
//            setCartCookie();
//            return List.of(savedCart);
//        } else {
//            // 기존 장바구니가 있는 경우
//            Cart existingCart = cartList.get(0);
//            if (!existingCart.getStore().getId().equals(storeId)) {
//                // 다른 가게의 메뉴 추가 시 기존 장바구니 초기화
//                cartRepository.deleteAll(cartList); // 모든 장바구니 삭제
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 상태 설정
//                throw new IllegalArgumentException("다른 가게의 메뉴를 추가할 수 없습니다."); // 필요 시 CustomException으로 대체 가능
//            }
//
//            // 동일한 가게일 경우 장바구니 업데이트
//            existingCart.updateCart(menuName, quantity, price);
//            Cart saved = cartRepository.save(existingCart);
//            return List.of(saved);
//        }
//    }
//
//
//    // 장바구니 수정
//    @Transactional
//    public List<CartResponseDto> updateCart(Long userId, String menuName, int quantity, int price) {
//        List<Cart> cartList = cartRepository.findByUserId(userId).stream()
//                // 유효한 장바구니만 조회
//                .filter(cart -> !isCartExpired(cart.getCreatedAt()))
//                .toList();
//
//        if (cartList.isEmpty()) {
//            throw new IllegalArgumentException("수정할 장바구니가 없습니다.");
//        }
//
//        // 유효한 첫 번째 장바구니 수정
//        Cart cart = cartList.get(0);
//        cart.updateCart(menuName, quantity, price);
//        cartRepository.save(cart);
//        setCartCookie();
//
//        return cartList.stream()
//                .map(c -> new CartResponseDto(c.getId(), c.getMenuName(), c.getPrice(), c.getQuantity()))
//                .toList();
//    }
//
//    // 장바구니 유효성 검사 후 쿠키 설정
//    private void setCartCookie() {
//        Cookie cartCookie = new Cookie("cart_valid", "true");
//        cartCookie.setMaxAge(24 * 60 * 60); // 1 day
//        cartCookie.setPath("/");
//        response.addCookie(cartCookie);
//    }

// ///////////////////////////////////////////////////////////////////////

//    // 장바구니 생성
//    @Transactional
//    public List<CartResponseDto> addToCart(Long userId, Long storeId, String menuName, int quantity, int price) {
//        List<Cart> cartList = cartRepository.findByUserId(userId).stream()
//                // 유효한 장바구니만 조회
//                .filter(cart -> !isCartExpired(cart.getCreatedAt()) && cart.isVisible())
//                .toList();
//
//        // 장바구니 없으면 새로운 장바구니 생성
//        if (cartList.isEmpty()) {
//            User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
//            Store store = storeRepository.findById(storeId).orElseThrow(EntityNotFoundException::new);
//
//            Cart cart = new Cart(user, store, menuName, quantity, price);
//            cartRepository.save(cart);
//            return List.of(new CartResponseDto(cart.getId(), cart.getMenuName(), cart.getPrice(), cart.getQuantity()));
//        } else {
//            // 기존 장바구니 업데이트
//            Cart existingCart = cartList.get(0);
//            if (!existingCart.getStore().getId().equals(storeId)) {
//                // 다른 가게의 메뉴 추가 시 예외 처리 CustomException 으로 처리
//                throw new IllegalArgumentException("다른 가게의 메뉴를 추가할 수 없습니다.");
//            }
//
//            existingCart.updateCart(menuName, quantity, price);
//            cartRepository.save(existingCart);
//            setCartCookie();
//
//            return cartRepository.findByUserId(userId).stream()
//                    // 유효한 항목만 반환!
//                    .filter(cart -> !isCartExpired(cart.getCreatedAt()))
//                    .map(cart -> new CartResponseDto(cart.getId(), cart.getMenuName(), cart.getPrice(), cart.getQuantity()))
//                    .toList();
//        }
//    }
//
//
//    // 하루가 지난 장바구니 제거 Get
//
////    @Scheduled(cron = "0 0 0 * * ?")
////    public void clearOldCarts() {
////        Date oneDayAgo = Date.from(Instant.now().minus(1, ChronoUnit.DAYS));
////
////        List<Cart> oldCarts = cartRepository.findAll().stream()
////                .filter(cart -> cart.getLastUpdated().before(oneDayAgo))
////                .toList();
////
////        cartRepository.deleteAll(oldCarts);
////    }

}
