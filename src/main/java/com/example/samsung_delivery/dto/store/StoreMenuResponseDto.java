package com.example.samsung_delivery.dto.store;

import com.example.samsung_delivery.entity.Menu;
import lombok.Getter;


@Getter
public class StoreMenuResponseDto {

    Long menuId;

    String menuName;

    int price;


    public StoreMenuResponseDto(Menu menu) {
        this.menuId = menu.getId();
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
    }
}
