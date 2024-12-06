package com.example.samsung_delivery.dto.coupon;

import lombok.Getter;

@Getter
public class CouponHistoryRequestDto {

    private final Long userId;

    private final Long storeId;

    private final Long couponId;

    public CouponHistoryRequestDto(Long userId, Long storeId, Long couponId) {
        this.userId = userId;
        this.storeId = storeId;
        this.couponId = couponId;
    }
}
