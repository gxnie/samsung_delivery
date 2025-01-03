package com.example.samsung_delivery.dto.user;


import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.UserRole;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;

    private final String email;

    private final UserRole role;

    public UserResponseDto(Long id, String email, UserRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
