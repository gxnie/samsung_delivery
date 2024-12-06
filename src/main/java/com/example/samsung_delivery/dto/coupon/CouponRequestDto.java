package com.example.samsung_delivery.dto.coupon;

import com.example.samsung_delivery.enums.CouponType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CouponRequestDto {

    @NotNull(message = "필수값 누락 입니다.")
    private final Long storeId;

    private final CouponType couponType;

    private final Integer discount;

    private final Integer maxIssued;

    private final LocalDate expiredAt;

    public CouponRequestDto(Long storeId, CouponType couponType, Integer discount, Integer maxIssued, LocalDate expiredAt) {
        this.storeId = storeId;
        this.couponType = couponType;
        this.discount = discount;
        this.maxIssued = maxIssued;
        this.expiredAt = expiredAt;
    }
}
