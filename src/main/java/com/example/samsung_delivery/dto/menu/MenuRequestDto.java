package com.example.samsung_delivery.dto.menu;

import lombok.Getter;

@Getter
public class MenuRequestDto {

    private Long storeId;
    private String menuName;
    private int price;

}
