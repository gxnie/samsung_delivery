package com.example.samsung_delivery.service;

import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.user.UserResponseDto;
import com.example.samsung_delivery.enums.UserRole;
import com.example.samsung_delivery.enums.UserStatus;


public interface UserService {

    UserResponseDto signUp(String email, String password, UserRole role);

    void deactivateUser(Long userId, String password);

    LoginResponseDto login(String email, String password);

}
