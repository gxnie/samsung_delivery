package com.example.samsung_delivery.dto.cart;

import com.example.samsung_delivery.entity.Cart;
import lombok.Getter;

@Getter
public class CartResponseDto {

    private Long cartId;

    private String menuName;

    private int price;

    private int quantity;

    public CartResponseDto(Long cartId, String menuName, int price, int quantity) {
        this.cartId = cartId;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
    }
}
