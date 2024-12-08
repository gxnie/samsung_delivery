package com.example.samsung_delivery.repository;


import com.example.samsung_delivery.entity.CouponHistory;
import com.example.samsung_delivery.enums.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponHistoryRepository extends JpaRepository<CouponHistory , Long> {


    long countByUser_IdAndCoupon_Id(Long userId, Long couponId);

    long countByCoupon_Id(Long couponId);

    Optional<CouponHistory> findByUser_IdAndCoupon_Id(Long userId, Long couponId);

    @Modifying
    @Query(value = "update CouponHistory ch set ch.status = :status where ch.coupon.id in " +
            "(select c.id from Coupon c where c.expiredAt < now())")
    void updateStatus(@Param("status")CouponStatus status);
}
