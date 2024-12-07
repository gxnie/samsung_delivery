package com.example.samsung_delivery.config;

import com.example.samsung_delivery.dto.cart.CartItemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartCookieEncoder {

    private final ObjectMapper objectMapper;

    public void setCartToCookies(List<CartItemDto> cartItems, HttpServletResponse response) {
        try {
            String cartJson = objectMapper.writeValueAsString(cartItems);
            String encodedCartJson = URLEncoder.encode(cartJson, "UTF-8");

            Cookie cookie = new Cookie("cart", encodedCartJson);
            cookie.setMaxAge(24 * 60 * 60); // 1일
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "장바구니 데이터를 처리하는 데 오류가 발생했습니다.",
                    e
            );
        }
    }

    public List<CartItemDto> getCartFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return new ArrayList<>(); // 장바구니가 비어 있는 경우 빈 리스트 반환
        }

        for (Cookie cookie : cookies) {
            if ("cart".equals(cookie.getName())) {
                try {
                    String decodedCartJson = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    return objectMapper.readValue(decodedCartJson, new TypeReference<List<CartItemDto>>() {});
                } catch (IOException e) {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "장바구니 데이터를 처리하는 데 오류가 발생했습니다.",
                            e
                    );
                }
            }
        }

        return new ArrayList<>(); // 쿠키에서 장바구니를 찾을 수 없는 경우 빈 리스트 반환
    }

    public void deleteCartCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("cart", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
