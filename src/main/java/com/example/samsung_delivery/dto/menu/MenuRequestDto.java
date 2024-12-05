package com.example.samsung_delivery.dto.menu;

import lombok.Getter;

@Getter
public class MenuRequestDto {

    private Long storeId;

    private String menuName;

    private int price;

    public MenuRequestDto(Long storeId, String menuName, int price) {
        this.storeId = storeId;
        this.menuName = menuName;
        this.price = price;
    }

    public MenuRequestDto() {}
}
