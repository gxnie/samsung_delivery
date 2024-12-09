package com.example.samsung_delivery.dto.coupon;

import com.example.samsung_delivery.entity.CouponHistory;
import com.example.samsung_delivery.enums.CouponStatus;
import lombok.Getter;

@Getter
public class CouponHistoryResponseDto {

    private Long id;

    private Long userId;

    private Long storeId;

    private Long couponId;

    private CouponStatus couponStatus;

    public CouponHistoryResponseDto(CouponHistory couponHistory){
        this.id = couponHistory.getId();
        this.userId = couponHistory.getUser().getId();
        this.storeId = couponHistory.getStore().getId();
        this.couponId = couponHistory.getCoupon().getId();
        this.couponStatus = couponHistory.getStatus();
    }
}
