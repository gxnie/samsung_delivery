package com.example.samsung_delivery.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequestDto {

    @NotNull(message = "필수값 누락 입니다.")
    private final Long userId;

    @NotNull(message = "필수값 누락 입니다.")
    private final Long menuId;

    @NotNull(message = "필수값 누락 입니다.")
    private final int usePoint;

    @NotNull(message = "필수값 누락 입니다.")
    private final int quantity;

    @NotNull(message = "필수값 누락 입니다.")
    private final String address;

    public OrderRequestDto(Long userId, Long menuId, int usePoint, Integer quantity, String address) {
        this.userId = userId;
        this.menuId = menuId;
        this.usePoint = usePoint;
        this.quantity = quantity;
        this.address = address;
    }
}
