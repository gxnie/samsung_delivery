package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.order.OrderRequestDto;
import com.example.samsung_delivery.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> changeOrderStatus(
            @PathVariable Long id,
            @RequestParam(required = true) String status){
        orderService.changeOrderStatus(id,status);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
