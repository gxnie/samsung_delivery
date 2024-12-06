package com.example.samsung_delivery.dto.coupon;

import com.example.samsung_delivery.entity.Coupon;
import com.example.samsung_delivery.enums.CouponType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponResponseDto {

    private Long id;

    private Long storeId;

    private CouponType couponType;

    private Integer discount;

    private Integer maxIssued;

    private LocalDateTime expiredAt;

    public CouponResponseDto(Coupon coupon){
        this.id = coupon.getId();
        this.storeId = coupon.getStore().getId();
        this.couponType = coupon.getCouponType();
        this.discount = coupon.getDiscount();
        this.maxIssued = coupon.getMaxIssued();
        this.expiredAt = coupon.getExpiredAt();
    }
}
