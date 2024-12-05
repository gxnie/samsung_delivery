package com.example.samsung_delivery.dto.cart;

import lombok.Getter;

@Getter
public class CartRequestDto {

    private Long userId;
    private Long storeId;
    private String menuName;
    private int quantity;
    private int price;
}