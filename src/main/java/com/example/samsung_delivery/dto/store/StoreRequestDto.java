package com.example.samsung_delivery.dto.store;

import com.example.samsung_delivery.enums.StoreStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreRequestDto {

    private Long userId;

    private String storeName;

    private LocalTime openTime;

    private LocalTime closeTime;

    private int minOrderPrice;

    public StoreRequestDto(Long userId, String storeName, LocalTime openTime, LocalTime closeTime, int minOrderPrice) {
        this.userId = userId;
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
    }

    public StoreRequestDto() {
    }
}
