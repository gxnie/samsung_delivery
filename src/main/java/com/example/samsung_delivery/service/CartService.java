package com.example.samsung_delivery.service;

import com.example.samsung_delivery.config.CartCookieEncoder;
import com.example.samsung_delivery.dto.cart.CartDto;
import com.example.samsung_delivery.dto.cart.CartItemDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartCookieEncoder cartCookieEncoder;

    // 장바구니 조회
    public CartDto getCart(HttpServletRequest request) {
        List<CartItemDto> items = cartCookieEncoder.getCartFromCookies(request);
        CartDto cartDto = new CartDto();
        cartDto.setItems(items);

        // 만료 여부 확인
        if (cartDto.isExpired()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "장바구니가 만료되었습니다.");
        }

        return cartDto;
    }

    // 장바구니 저장
    public CartDto saveCart(HttpServletResponse response, CartDto cartDto) {
        validateCart(cartDto);

        // 총합 계산
        int totalPrice = cartDto.calculateTotalPrice();
        // 디버깅용 출력
        System.out.println("장바구니 총합: " + totalPrice);

        cartDto.setLastUpdated(LocalDateTime.now());
        cartCookieEncoder.setCartToCookies(cartDto.getItems(), response);

        return cartDto;
    }

    // 장바구니 비우기
    public void clearCart(HttpServletResponse response) {
        cartCookieEncoder.deleteCartCookie(response);
    }

    // 장바구니 유효성 검증
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
