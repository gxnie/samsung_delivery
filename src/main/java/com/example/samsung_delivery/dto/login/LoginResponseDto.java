package com.example.samsung_delivery.dto.login;

import com.example.samsung_delivery.enums.UserRole;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final Long userId;

    private final String email;

    private final UserRole userRole;

    public LoginResponseDto(Long userId, String email ,UserRole userRole) {
        this.userId = userId;
        this.email = email;
        this.userRole = userRole;
    }
}
