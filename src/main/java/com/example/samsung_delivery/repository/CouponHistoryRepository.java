package com.example.samsung_delivery.repository;


import com.example.samsung_delivery.entity.CouponHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponHistoryRepository extends JpaRepository<CouponHistory , Long> {


    long countByUser_IdAndCoupon_Id(Long userId, Long couponId);

    long countByCoupon_Id(Long couponId);
}
