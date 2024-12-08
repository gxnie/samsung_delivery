package com.example.samsung_delivery.dto.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreTotalResponseDto {
    private LocalDate date;

    private YearMonth month;

    private List<StoreDailyResponseDto> storeDailyResponseDto;

    private List<StoreMonthlyResponseDto> storeMonthlyResponseDto;

    public StoreTotalResponseDto(LocalDate date, List<StoreDailyResponseDto> storeDailyResponseDto) {
        this.date = date;
        this.storeDailyResponseDto = storeDailyResponseDto;
    }

    public StoreTotalResponseDto(YearMonth month, List<StoreMonthlyResponseDto> storeMonthlyResponseDto) {
        this.month = month;
        this.storeMonthlyResponseDto = storeMonthlyResponseDto;
    }
}
