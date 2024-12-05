package com.example.samsung_delivery.entity;

import com.example.samsung_delivery.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "`order`")
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Integer quantity;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(nullable = false)
    private String address;

    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> review;

    public Order(){}

    public Order(Integer quantity , Integer totalPrice , String address){
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.address = address;
    }

    public void updateStatus(OrderStatus status){
        this.status = status;
    }


}
