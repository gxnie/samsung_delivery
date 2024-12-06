package com.example.samsung_delivery.config;

import com.example.samsung_delivery.dto.cart.CartDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.Base64;

public class CartCookieEncoder {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()) // Java 8 DateTime 지원
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static String encode(CartDto cart) {
        try {
            return Base64.getEncoder().encodeToString(objectMapper.writeValueAsString(cart).getBytes());
        } catch (JsonProcessingException exception) {
            exception.printStackTrace(); // 에러 로그 출력
            throw new RuntimeException("Failed to encode cart data", exception);
        }
    }

    public static CartDto decode(String encodedCart) {
        try {
            String decoded = new String(Base64.getDecoder().decode(encodedCart));
            return objectMapper.readValue(decoded, CartDto.class);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to decode cart data", exception);
        }
    }
}
