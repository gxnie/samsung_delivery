package com.example.samsung_delivery.controller;


import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.LoginRequestDto;
import com.example.samsung_delivery.dto.LoginResponseDto;
import com.example.samsung_delivery.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class LoginController {

    private final LoginService loginService;

    // 사용자 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto, // 로그인 요청 데이터
            HttpServletRequest servletRequest // HTTP 요청 객체
    ) {
        // 로그인 서비스 호출
        LoginResponseDto loginResponseDto = loginService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // 세션 생성 및 사용자 정보 저장
        HttpSession httpSession = servletRequest.getSession();
        httpSession.setAttribute(Const.LOGIN_USER, loginResponseDto);

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK); // 로그인 성공 결과 반환
    }
}
