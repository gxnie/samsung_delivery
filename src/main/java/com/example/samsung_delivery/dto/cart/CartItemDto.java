package com.example.samsung_delivery.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long menuId;
    private String menuName;
    private int quantity;
    private int price;
    private int discount; // 추가
    private List<String> options; // 추가
}



