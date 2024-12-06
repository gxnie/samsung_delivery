package com.example.samsung_delivery.entity;


import com.example.samsung_delivery.enums.CouponType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "`coupon`")
public class Coupon extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    @Column(nullable = false)
    private Integer discount;

    @Column(nullable = false)
    private Integer maxIssued;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public Coupon(){}

    public Coupon(int discount , int maxIssued , LocalDateTime expiredAt){
        this.discount = discount;
        this.maxIssued = maxIssued;
        this.expiredAt = expiredAt;
    }
}

