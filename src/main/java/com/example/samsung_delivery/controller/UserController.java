package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.login.LoginRequestDto;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.user.UserRequestDto;
import com.example.samsung_delivery.dto.user.UserResponseDto;
import com.example.samsung_delivery.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userDto) {
        UserResponseDto userResponseDto =
                userService.signUp(userDto.getEmail(), userDto.getPassword(), userDto.getRole());

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    // 사용자 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto, // 로그인 요청 데이터
            HttpServletRequest servletRequest // HTTP 요청 객체
    ) {
        // 로그인 서비스 호출
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // 세션 생성 및 사용자 정보 저장
        HttpSession httpSession = servletRequest.getSession();
        httpSession.setAttribute(Const.LOGIN_USER, loginResponseDto);

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK); // 로그인 성공 결과 반환
    }

    // 회원탈퇴
    @PatchMapping("/{userId}")
    public ResponseEntity<String> deactivateUser(
            @PathVariable Long userId,
            @RequestBody UserRequestDto userDto) {

            userService.deactivateUser(userId, userDto.getPassword());
            return ResponseEntity.ok("회원 탈퇴되었습니다.");
    }
}
