package com.example.samsung_delivery.dto.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryTotalResponseDto {
    private LocalDate date;

    private YearMonth month;

    private List<CategoryDailyResponseDto> categoryDailyResponseDto;

    private List<CategoryMonthlyResponseDto> categoryMonthlyResponseDto;

    public CategoryTotalResponseDto(LocalDate date, List<CategoryDailyResponseDto> categoryDailyResponseDto) {
        this.date = date;
        this.categoryDailyResponseDto = categoryDailyResponseDto;
    }

    public CategoryTotalResponseDto(YearMonth month, List<CategoryMonthlyResponseDto> categoryMonthlyResponseDto) {
        this.month = month;
        this.categoryMonthlyResponseDto = categoryMonthlyResponseDto;
    }
}
