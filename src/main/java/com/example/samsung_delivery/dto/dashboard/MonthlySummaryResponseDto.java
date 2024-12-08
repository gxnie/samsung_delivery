package com.example.samsung_delivery.dto.dashboard;

import lombok.Getter;

import java.time.LocalDate;
import java.time.YearMonth;

@Getter
public class MonthlySummaryResponseDto {

    private YearMonth month;

    private Long monthlyTotalOrderCount;

    private Long monthlyTotalOrderPrice;

    public MonthlySummaryResponseDto(Long monthlyTotalOrderCount, Long monthlyTotalOrderPrice) {
        this.monthlyTotalOrderCount = monthlyTotalOrderCount;
        this.monthlyTotalOrderPrice = monthlyTotalOrderPrice;
    }

    public void addMonth(YearMonth month) {this.month = month;}
}
