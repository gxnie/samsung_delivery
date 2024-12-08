package com.example.samsung_delivery.controller;


import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.dashboard.*;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.entity.Order;
import com.example.samsung_delivery.error.errorcode.ErrorCode;
import com.example.samsung_delivery.error.exception.CustomException;
import com.example.samsung_delivery.repository.OrderRepository;
import com.example.samsung_delivery.service.DashBoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {
    private DashBoardService dashBoardService;
    private OrderRepository orderRepository;

    public DashBoardController(DashBoardService dashBoardService, OrderRepository orderRepository) {
        this.dashBoardService = dashBoardService;
        this.orderRepository = orderRepository;
    }


    //전체 일간 주문 수, 총액 조회
    @GetMapping("/orders/daily-summary")
    public ResponseEntity<DailySummaryResponseDto> getDailySummary(@RequestParam(value = "date", required = true) String date) {


        LocalDate inputDate = LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();

        if (inputDate.isAfter(currentDate)) {
            throw new CustomException(ErrorCode.INVALID_DATE_EXCEPTION);
        }

        DailySummaryResponseDto summaryDto = dashBoardService.getDailyOrderSummary(date);

        return new ResponseEntity<>(summaryDto, HttpStatus.OK);
    }


    //전체 월간 주문 수, 총액 조회
    @GetMapping("/orders/monthly-summary")
    public ResponseEntity<MonthlySummaryResponseDto> getMonthlySummary(@RequestParam(value = "month", required = true) String month) {
        YearMonth inputYearMonth = YearMonth.parse(month);
        YearMonth currentYearMonth = YearMonth.now();

        if(inputYearMonth.isAfter(currentYearMonth)) {
            throw new CustomException(ErrorCode.INVALID_DATE_EXCEPTION);
        }

        MonthlySummaryResponseDto summaryResponseDto = dashBoardService.getMonthlyOrderSummary(month);

        return new ResponseEntity<>(summaryResponseDto, HttpStatus.OK);
    }

    //가게별 일간 주문 수, 총액 조회
    @GetMapping("/stores/orders/daily-summary")
    public ResponseEntity<StoreTotalResponseDto> getStoreDailySummary(@RequestParam(value = "date", required = true) String date) {
        LocalDate inputDate = LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();
        if (inputDate.isAfter(currentDate)) {
            throw new CustomException(ErrorCode.INVALID_DATE_EXCEPTION);
        }

        StoreTotalResponseDto storeDailyResponseDto = dashBoardService.getStoreDailySummary(date);

        return ResponseEntity.ok(storeDailyResponseDto);
    }

    //가게별 월간 주문 수, 총액 조회
    @GetMapping("/stores/orders/monthly-summary")
    public ResponseEntity<StoreTotalResponseDto> getStoreMonthlySummary(@RequestParam(value = "month", required = true) String month) {
        YearMonth inputYearMonth = YearMonth.parse(month);
        YearMonth currentYearMonth = YearMonth.now();
        if(inputYearMonth.isAfter(currentYearMonth)) {
            throw new CustomException(ErrorCode.INVALID_DATE_EXCEPTION);
        }

        StoreTotalResponseDto storeMonthlyResponseDto = dashBoardService.getStoreMonthlySummary(month);

        return ResponseEntity.ok(storeMonthlyResponseDto);
    }

    //카테고리별 일간 주문 수, 총액 조회
    @GetMapping("/category/orders/daily-summary")
    public ResponseEntity<CategoryTotalResponseDto> getCategoryDailySummary(@RequestParam(value = "date", required = true) String date) {
        LocalDate inputDate = LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();
        if (inputDate.isAfter(currentDate)) {
            throw new CustomException(ErrorCode.INVALID_DATE_EXCEPTION);
        }

        CategoryTotalResponseDto categoryDto = dashBoardService.getCategoryDailySummary(date);

        return ResponseEntity.ok(categoryDto);
    }

    //카테고리별 월간 주문 수, 총액 조회
    @GetMapping("/category/orders/monthly-summary")
    public ResponseEntity<CategoryTotalResponseDto> getCategoryMonthlySummary(@RequestParam(value = "month", required = true) String month) {
        YearMonth inputYearMonth = YearMonth.parse(month);
        YearMonth currentYearMonth = YearMonth.now();
        if(inputYearMonth.isAfter(currentYearMonth)) {
            throw new CustomException(ErrorCode.INVALID_DATE_EXCEPTION);
        }
        CategoryTotalResponseDto categoryDto = dashBoardService.getCategoryMonthlySummary(month);

        return ResponseEntity.ok(categoryDto);
    }

}