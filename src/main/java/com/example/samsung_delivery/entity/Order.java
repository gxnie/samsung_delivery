package com.example.samsung_delivery.entity;

import com.example.samsung_delivery.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @Setter
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private int quantity;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(nullable = false)
    private String address;

    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @ColumnDefault("0")
    private int usePoint;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> review;

    public Order(){}

    public Order(Integer quantity ,Integer usePoint ,Integer totalPrice , String address){
        this.quantity = quantity;
        this.usePoint = usePoint;
        this.totalPrice = totalPrice;
        this.address = address;
    }

    public void updateStatus(OrderStatus status){
        this.status = status;
    }


}
