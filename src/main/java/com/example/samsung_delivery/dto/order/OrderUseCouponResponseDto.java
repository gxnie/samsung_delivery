package com.example.samsung_delivery.dto.order;

import com.example.samsung_delivery.entity.Order;
import com.example.samsung_delivery.enums.OrderStatus;
import lombok.Getter;

@Getter
public class OrderUseCouponResponseDto {

    private Long id;

    private Long menuId;

    private Long userId;

    private Long couponId;

    private String orderNum;

    private Integer quantity;

    private Integer usePoint;

    private Integer remainPoint;

    private Integer totalPrice;

    private String address;

    private OrderStatus status;

    public OrderUseCouponResponseDto (Order order , int remainPoint  , Long couponId ){
        this.id = order.getId();
        this.menuId = order.getMenu().getId();
        this.userId = order.getUser().getId();
        this.couponId = couponId;
        this.orderNum = order.getOrderNumber();
        this.quantity = order.getQuantity();
        this.usePoint = order.getUsePoint();
        this.remainPoint = remainPoint;
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress();
        this.status = order.getStatus();
    }

}