package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.dto.UserRequestDto;
import com.example.samsung_delivery.dto.UserResponseDto;
import com.example.samsung_delivery.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDto));
    }

    // 회원탈퇴
    @PatchMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("회원탈퇴 되었습니다.");

    }


}
