package com.example.samsung_delivery.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequestDto {

    @NotNull(message = "userId 은 필수값 입니다.")
    private final Long userId;

    @NotNull(message = "menuId 은 필수값 입니다.")
    private final Long menuId;


    private final Integer quantity;

    @NotNull(message = "address 은 필수값 입니다.")
    private final String address;

    public OrderRequestDto(Long userId, Long menuId, Integer quantity, String address) {
        this.userId = userId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.address = address;
    }
}
