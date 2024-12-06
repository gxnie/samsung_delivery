package com.example.samsung_delivery.entity;

import com.example.samsung_delivery.enums.CouponStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "`coupon_history`")
public class CouponHistory extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Setter
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status;

    public CouponHistory (User user , Store store , Coupon coupon){
        this.setUser(user);
        this.setStore(store);
        this.setCoupon(coupon);
    }

    public CouponHistory() {

    }
}
