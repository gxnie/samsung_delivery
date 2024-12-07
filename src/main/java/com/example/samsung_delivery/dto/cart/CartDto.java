package com.example.samsung_delivery.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long userId;
    private Long storeId;
    private List<CartItemDto> items = new ArrayList<>();
    private LocalDateTime lastUpdated = LocalDateTime.now();
    private int totalPrice;
    private String status;

    public boolean isExpired() {
        return lastUpdated.plusDays(1).isBefore(LocalDateTime.now());
    }

    public int calculateTotalPrice() {
        return items.stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}

