package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.dto.user.UserRequestDto;
import com.example.samsung_delivery.dto.user.UserResponseDto;
import com.example.samsung_delivery.service.LoginService;
import com.example.samsung_delivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userDto) {
        UserResponseDto userResponseDto =
                userService.signUp(userDto.getEmail(), userDto.getPassword(), userDto.getRole());

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
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
