package com.example.samsung_delivery.dto.store;

import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.enums.StoreStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreResponseDto {

    private Long id;

    private Long userId;

    private String storeName;

    private LocalTime openTime;

    private LocalTime closeTime;

    private int minOrderPrice;

    private StoreStatus status = StoreStatus.ACTIVE;

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.userId = store.getUser().getId();
        this.storeName = store.getStoreName();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minOrderPrice = store.getMinOrderPrice();
        this.status = store.getStatus();
    }
}
