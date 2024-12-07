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

    /**
     * 장바구니 데이터를 쿠키에 저장
     * @param cartItems 저장할 장바구니 아이템 리스트
     * @param response HTTP 응답 객체
     */
    public void setCartToCookies(List<CartItemDto> cartItems, HttpServletResponse response) {
        try {
            // 객체를 JSON 문자열로 변환
            String cartJson = objectMapper.writeValueAsString(cartItems);

            // JSON 데이터를 UTF-8로 인코딩
            String encodedCartJson = URLEncoder.encode(cartJson, "UTF-8");

            // 쿠키 생성 및 설정
            Cookie cookie = new Cookie("cart", encodedCartJson);

            // 1일 유효기간 설정
            cookie.setMaxAge(24 * 60 * 60);

            // 모든 경로에서 쿠키 접근 가능
            cookie.setPath("/");

            // 클라이언트 스크립트로 접근 불가능하게 설정
            cookie.setHttpOnly(true);

            // 쿠키를 응답에 추가
            response.addCookie(cookie);
        } catch (JsonProcessingException | UnsupportedEncodingException exception) {
            // JSON 변환이나 인코딩 중 오류 발생 시 예외 처리
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "장바구니 데이터를 처리하는 데 오류가 발생했습니다.",
                    exception
            );
        }
    }

    /**
     * HTTP 요청에서 장바구니 데이터를 쿠키로부터 읽어 반환
     * @param request HTTP 요청 객체
     * @return 장바구니 아이템 리스트
     */
    public List<CartItemDto> getCartFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        // 요청에 쿠키가 없는 경우 빈 리스트 반환 (장바구니가 비어있는 경우)
        if (cookies == null) {
            return new ArrayList<>();
        }

        // "cart"라는 이름의 쿠키를 탐색
        for (Cookie cookie : cookies) {
            if ("cart".equals(cookie.getName())) {
                try {
                    // 쿠키 값을 디코딩하여 JSON 형식의 문자열로 변환
                    String decodedCartJson = URLDecoder.decode(cookie.getValue(), "UTF-8");

                    // JSON 문자열을 객체 리스트로 변환
                    return objectMapper.readValue(decodedCartJson, new TypeReference<List<CartItemDto>>() {});
                } catch (IOException exception) {
                    // JSON 파싱 중 오류 발생 시 예외 처리
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "장바구니 데이터를 처리하는 데 오류가 발생했습니다.",
                            exception
                    );
                }
            }
        }

        // "cart" 쿠키가 없는 경우 빈 리스트 반환 (쿠키에서 장바구니를 찾을 수 없는 경우)
        return new ArrayList<>();
    }

    /**
     * 쿠키에 저장된 장바구니 데이터를 삭제
     * @param response HTTP 응답 객체
     */
    public void deleteCartCookie(HttpServletResponse response) {
        // "cart" 이름의 쿠키를 삭제하기 위해 MaxAge를 0으로 설정
        Cookie cookie = new Cookie("cart", null);

        // 쿠키 즉시 삭제
        cookie.setMaxAge(0);

        // 삭제 경로 설정
        cookie.setPath("/");

        // HttpOnly 속성 유지
        cookie.setHttpOnly(true);

        // 응답에 삭제된 쿠키 추가
        response.addCookie(cookie);
    }
}
