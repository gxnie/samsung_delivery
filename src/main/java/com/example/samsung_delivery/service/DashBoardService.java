package com.example.samsung_delivery.service;

import com.example.samsung_delivery.dto.dashboard.*;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.enums.StoreStatus;
import com.example.samsung_delivery.repository.OrderRepository;
import com.example.samsung_delivery.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashBoardService {

    private OrderRepository orderRepository;

    public DashBoardService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //일일 전체 주문 수, 총액 조회
    @Transactional(readOnly = true)
    public DailySummaryResponseDto getDailyOrderSummary(String date) {

        //입력 받은 date를 LocalDate로 변환
        LocalDate modifiedDate = LocalDate.parse(date);
        //요청일의 시작과 끝 계산
        LocalDateTime startOfDay = LocalDate.parse(date).atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.now().plusDays(1);

        DailySummaryResponseDto dailySummaryDto = orderRepository.findByModifiedAt(startOfDay, endOfDay);

        //조회된 데이터를 LocalDate로 매핑
        dailySummaryDto.addDate(LocalDate.from(modifiedDate));

        return dailySummaryDto;
    }

    //월간 전체 주문 수, 총액 조회
    @Transactional(readOnly = true)
    public MonthlySummaryResponseDto getMonthlyOrderSummary(String month) {
        // 입력 받은 month를 YearMonth로 변환
        YearMonth requestMonth = YearMonth.parse(month);

        // 요청 월의 시작과 끝 시간 계산
        LocalDateTime startOfMonth = requestMonth.atDay(1).atStartOfDay(); // 해당 월의 첫날 시작
        LocalDateTime endOfMonth = requestMonth.atEndOfMonth().atTime(23, 59, 59); // 해당 월의 마지막 날 종료

        // 리포지토리에서 데이터 조회
        MonthlySummaryResponseDto monthlySummaryResponseDto = orderRepository.findByMonth(startOfMonth, endOfMonth);

        // 조회된 데이터를 다시 요청 Month로 매핑
        monthlySummaryResponseDto.addMonth(requestMonth);

        return monthlySummaryResponseDto;
    }

    //가게별 일일 주문 수, 총액 조회
    @Transactional(readOnly = true)
    public StoreTotalResponseDto getStoreDailySummary(String date) {

        LocalDate modifiedDate = LocalDate.parse(date);

        LocalDateTime startOfMonth = LocalDate.parse(date).atStartOfDay();
        LocalDateTime endOfMonth = LocalDateTime.now().plusDays(1);

        List<StoreDailyResponseDto> storeDailyResponseDto = orderRepository.findByStore(startOfMonth, endOfMonth);

        return new StoreTotalResponseDto(modifiedDate, storeDailyResponseDto);

    }

    //가게별 월간 주문 수, 총액 조회
    @Transactional(readOnly = true)
    public StoreTotalResponseDto getStoreMonthlySummary(String month) {
        YearMonth requestMonth = YearMonth.parse(month);
        LocalDateTime startOfMonth = requestMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = requestMonth.atEndOfMonth().atTime(23, 59, 59);

        List<StoreMonthlyResponseDto> storeMonthlyResponseDtos = orderRepository.findByStoreMonthly(startOfMonth, endOfMonth);

        return new StoreTotalResponseDto(requestMonth, storeMonthlyResponseDtos);
    }

    //카테고리별 일간 주문 수, 총액 조회
    @Transactional(readOnly = true)
    public CategoryTotalResponseDto getCategoryDailySummary(String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        LocalDateTime startOfDay = parsedDate.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        // 데이터 조회
        List<CategoryDailyResponseDto> dailySummaries = orderRepository.findByCategoryDaily(startOfDay, endOfDay);

        // DTO 생성 및 반환
        return new CategoryTotalResponseDto(parsedDate, dailySummaries);
    }

    //카테고리별 월간 주문 수, 총액 조회
    @Transactional(readOnly = true)
    public CategoryTotalResponseDto getCategoryMonthlySummary(String month) {
        YearMonth requestMonth = YearMonth.parse(month);
        LocalDateTime startOfMonth = requestMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = requestMonth.atEndOfMonth().atTime(23, 59, 59);

        List<CategoryMonthlyResponseDto> monthlySummaries = orderRepository.findByCategoryMonthly(startOfMonth, endOfMonth);

        return new CategoryTotalResponseDto(requestMonth, monthlySummaries);
    }

}
