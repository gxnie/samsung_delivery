package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.dto.login.LoginRequestDto;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class LoginController {

    private final UserService userService;

//    // 로그인
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto,
//                                            HttpServletRequest request) {
//        User loginedUser = userService.loginUser(loginRequestDto);
//        HttpSession session = request.getSession();
//        session.setAttribute("SESSION_KEY", loginedUser.getUserId());
//        return ResponseEntity.ok().body("정상적으로 로그인되었습니다.");
//    }


}
