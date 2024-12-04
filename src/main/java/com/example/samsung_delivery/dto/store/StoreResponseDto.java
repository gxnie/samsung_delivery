package com.example.samsung_delivery.dto.store;

import com.example.samsung_delivery.entity.Menu;
import com.example.samsung_delivery.entity.Store;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StoreResponseDto {

    private Long id;

    private Long userId;

    private String storeName;

    private LocalTime openTime;

    private LocalTime closeTime;

    private int minOrderPrice;

//    private StoreStatus status = StoreStatus.ACTIVE;

    private List<StoreMenuResponseDto> menus = new ArrayList<>();

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.userId = store.getUser().getId();
        this.storeName = store.getStoreName();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minOrderPrice = store.getMinOrderPrice();
//        this.status = store.getStatus();

//        this.menus = new ArrayList<>();
//
//        for (Menu menu:store.getMenus()) {
//            this.menus.add(new StoreMenuResponseDto(menu));
//        }
    }
}
