package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CouponRepository extends JpaRepository<Coupon ,Long> {
}
