package com.example.samsung_delivery.entity;

import com.example.samsung_delivery.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;


@Entity
@Getter
public class Store extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Menu> menus;

    @Column(nullable = false)
    String storeName;

    @Column(nullable = false)
    LocalTime openTime;

    @Column(nullable = false)
    LocalTime closeTime;

    @Column(nullable = false)
    int minOrderPrice;

    @Enumerated(EnumType.STRING)
    private StoreStatus status = StoreStatus.ACTIVE;

    public Store(User user, String storeName, LocalTime openTime, LocalTime closeTime, int minOrderPrice) {
        this.user = user;
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
    }

    public Store() {
    }

    public void update(String storeName, LocalTime openTime, LocalTime closeTime, int minOrderPrice) {
        this.storeName = storeName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
    }
}
