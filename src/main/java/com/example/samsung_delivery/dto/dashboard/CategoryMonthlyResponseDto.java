package com.example.samsung_delivery.dto.dashboard;

import lombok.Getter;

@Getter
public class CategoryMonthlyResponseDto {
    private String category;

    private Long monthlyTotalCount;

    private Long monthlyTotalPrice;

    public CategoryMonthlyResponseDto(String category, Long monthlyTotalCount, Long monthlyTotalPrice) {
        this.category = category;
        this.monthlyTotalCount = monthlyTotalCount;
        this.monthlyTotalPrice = monthlyTotalPrice;
    }
}
