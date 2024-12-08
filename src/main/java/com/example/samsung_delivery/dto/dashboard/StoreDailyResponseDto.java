package com.example.samsung_delivery.dto.dashboard;

import com.example.samsung_delivery.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;

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
