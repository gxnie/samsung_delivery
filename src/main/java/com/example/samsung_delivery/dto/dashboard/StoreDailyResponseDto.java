package com.example.samsung_delivery.dto.dashboard;

import lombok.Getter;

@Getter
public class StoreDailyResponseDto {

    private Long storeId;

    private Long dailyTotalOrderCount;

    private Long dailyTotalOrderPrice;

    public StoreDailyResponseDto(Long storeId, Long dailyTotalOrderCount, Long dailyTotalOrderPrice) {
        this.storeId = storeId;
        this.dailyTotalOrderCount = dailyTotalOrderCount;
        this.dailyTotalOrderPrice = dailyTotalOrderPrice;
    }

}
