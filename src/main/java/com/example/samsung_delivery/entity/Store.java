package com.example.samsung_delivery.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false)
    private Integer minOrderPrice;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private String open;

    @Column(nullable = false)
    private String close;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
