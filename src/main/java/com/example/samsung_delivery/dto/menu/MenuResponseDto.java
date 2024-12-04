package com.example.samsung_delivery.dto.menu;

import lombok.Getter;

@Getter
public class MenuResponseDto {

    private final Long menuId;

    private final Long storeId;

    private final String menuName;

    private final int price;

    public MenuResponseDto(Long menuId, Long storeId, String menuName, int price) {
        this.menuId = menuId;
        this.storeId = storeId;
        this.menuName = menuName;
        this.price = price;
    }
}
