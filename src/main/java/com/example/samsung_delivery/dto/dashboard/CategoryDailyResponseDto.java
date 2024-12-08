package com.example.samsung_delivery.dto.dashboard;

import lombok.Getter;

@Getter
public class CategoryDailyResponseDto {

    private String category;

    private Long dailyTotalCount;

    private Long dailyTotalPrice;

    public CategoryDailyResponseDto(String category, Long dailyTotalCount, Long dailyTotalPrice) {
        this.category = category;
        this.dailyTotalCount = dailyTotalCount;
        this.dailyTotalPrice = dailyTotalPrice;
    }
}
