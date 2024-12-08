package com.example.samsung_delivery.dto.store;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreRequestDto {

    private Long userId;

    private String category;

    private String storeName;

    private LocalTime openTime;

    private LocalTime closeTime;

    private int minOrderPrice;

    public StoreRequestDto(Long userId, String category, String storeName, LocalTime openTime, LocalTime closeTime, int minOrderPrice) {
        this.userId = userId;
        this.category = category;
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
    }

    public StoreRequestDto() {
    }
}
