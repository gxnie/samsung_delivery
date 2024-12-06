package com.example.samsung_delivery.config;

import com.example.samsung_delivery.dto.cart.CartDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class CartCookieEncoder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String encode(List<CartDto> cart) {
        try {
            return Base64.getEncoder().encodeToString(objectMapper.writeValueAsString(cart).getBytes());
        } catch (JsonProcessingException exception) {
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
