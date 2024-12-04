package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.order.OrderRequestDto;
import com.example.samsung_delivery.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequestDto requestDto,
            HttpServletRequest httpServletRequest
            ){
        HttpSession session = httpServletRequest.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto)session.getAttribute(Const.LOGIN_USER);

        return ResponseEntity.ok().body(orderService.save(loginUser.getUserId(),requestDto.getMenuId(),requestDto.getQuantity(),requestDto.getAddress()));
    }
}
