package com.example.samsung_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "order")
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @Setter
//    @ManyToOne
//    @JoinColumn(name = "menu_id")
//    private Menu menu;

    private Integer quantity;

    private Integer totalPrice;

    private String address;

    private String status;

    public Order(){}



}
