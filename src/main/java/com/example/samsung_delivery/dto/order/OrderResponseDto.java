package com.example.samsung_delivery.dto.order;

import com.example.samsung_delivery.entity.Order;
import lombok.Getter;

@Getter
public class OrderResponseDto {

    private Long id;

    private Long menuId;

    private Long userId;

    private Integer quantity;

    private Integer totalPrice;

    private String address;

    public OrderResponseDto (Order order){
        this.id = order.getId();
        this.menuId = order.getMenu().getId();
        this.userId = order.getUser().getId();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress();
    }


}
