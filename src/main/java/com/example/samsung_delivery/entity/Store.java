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

    @ManyToOne
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Menu> menus;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closeTime;

    @Column(nullable = false)
    private int minOrderPrice;

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    private StoreStatus status = StoreStatus.ACTIVE;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> review;

//    @OneToMany(mappedBy = "store")
//    private List<Cart> carts = new ArrayList<>();


    public Store(User user,String category, String storeName, LocalTime openTime, LocalTime closeTime, int minOrderPrice) {
        this.user = user;
        this.category = category;
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
