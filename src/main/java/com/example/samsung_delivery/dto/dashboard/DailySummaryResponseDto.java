package com.example.samsung_delivery.dto.dashboard;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DailySummaryResponseDto {

    private LocalDate date;

    private Long dailyTotalOrderCount;

    private Long dailyTotalOrderPrice;

    public DailySummaryResponseDto(Long dailyTotalOrderCount, Long dailyTotalOrderPrice ) {
        this.dailyTotalOrderCount = dailyTotalOrderCount;
        this.dailyTotalOrderPrice = dailyTotalOrderPrice;
    }


    public void addDate(LocalDate date) {
        this.date = date;
    }
}
