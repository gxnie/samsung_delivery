package com.example.samsung_delivery.entity;

import com.example.samsung_delivery.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    private String address;


    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

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
