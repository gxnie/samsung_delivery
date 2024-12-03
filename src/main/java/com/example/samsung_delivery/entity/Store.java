package com.example.samsung_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalTime;


@Entity
@Getter
public class Store extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //User Entity에 @Entity 선언 시 해결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

    String storeName;

    LocalTime openTime;

    LocalTime closeTime;

    int minOrderPrice;

    public Store(User user, String storeName, LocalTime openTime, LocalTime closeTime, int minOrderPrice) {
        this.user = user;
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
    }

    public Store() {
    }
}
