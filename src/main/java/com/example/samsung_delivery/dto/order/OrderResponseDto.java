package com.example.samsung_delivery.dto.order;

import com.example.samsung_delivery.entity.Order;
import com.example.samsung_delivery.enums.OrderStatus;
import lombok.Getter;

@Getter
public class OrderResponseDto {

    private Long id;

    private Long menuId;

    private Long userId;

    private String orderNum;

    private Integer quantity;

    private Integer usePoint;

    private Integer remainPoint;

    private Integer totalPrice;

    private String address;

    private OrderStatus status;

    public OrderResponseDto (Order order , int remainPoint){
        this.id = order.getId();
        this.menuId = order.getMenu().getId();
        this.userId = order.getUser().getId();
        this.orderNum = order.getOrderNumber();
        this.quantity = order.getQuantity();
        this.usePoint = order.getUsePoint();
        this.remainPoint = remainPoint;
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress();
        this.status = order.getStatus();
    }

    public OrderResponseDto (Order order){
        this.id = order.getId();
        this.menuId = order.getMenu().getId();
        this.userId = order.getUser().getId();
        this.orderNum = order.getOrderNumber();
        this.quantity = order.getQuantity();
        this.usePoint = order.getUsePoint();
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress();
        this.status = order.getStatus();
    }


}
