package com.example.samsung_delivery.service;

import com.example.samsung_delivery.dto.LoginResponseDto;

public interface LoginService {

    LoginResponseDto login(String email, String password);
}
