package com.example.samsung_delivery.dto;

import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.UserRole;
import com.example.samsung_delivery.enums.UserStatus;
import lombok.Getter;

@Getter
public class UserRequestDto {

    private final String email;

    private final String password;

    private final UserRole role;


    public UserRequestDto(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
