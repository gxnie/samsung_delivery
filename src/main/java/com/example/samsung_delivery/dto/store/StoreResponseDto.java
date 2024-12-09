package com.example.samsung_delivery.dto.store;

import com.example.samsung_delivery.dto.menu.MenuResponseDto;
import com.example.samsung_delivery.entity.Store;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import java.time.LocalTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreResponseDto {

    private Long id;

    private Long userId;

    private String category;

    private String storeName;

    private LocalTime openTime;

    private LocalTime closeTime;

    private int minOrderPrice;

    private List<MenuResponseDto> menus;

    public StoreResponseDto(Store store, List<MenuResponseDto> menus) {
        this.id = store.getId();
        this.userId = store.getUser().getId();
        this.category = store.getCategory();
        this.storeName = store.getStoreName();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minOrderPrice = store.getMinOrderPrice();
        this.menus = menus;
    }

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.userId = store.getUser().getId();
        this.category = store.getCategory();
        this.storeName = store.getStoreName();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minOrderPrice = store.getMinOrderPrice();
    }
}
