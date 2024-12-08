package com.example.samsung_delivery.dto.dashboard;

import lombok.Getter;

@Getter
public class StoreMonthlyResponseDto {
    private Long storeId;

    private Long monthlyTotalOrderCount;

    private Long monthlyTotalOrderPrice;

    public StoreMonthlyResponseDto(Long storeId, Long monthlyTotalOrderCount, Long monthlyTotalOrderPrice) {
        this.storeId = storeId;
        this.monthlyTotalOrderCount = monthlyTotalOrderCount;
        this.monthlyTotalOrderPrice = monthlyTotalOrderPrice;
    }
}
