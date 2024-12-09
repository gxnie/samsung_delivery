package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.cart.CartDto;
import com.example.samsung_delivery.dto.cart.CartItemDto;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.order.OrderResponseDto;
import com.example.samsung_delivery.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 조회 : 현재 장바구니 데이터를 반환
    @GetMapping
    public ResponseEntity<CartDto> getCart(HttpServletRequest request) {
        CartDto cartDto = cartService.getCart(request);
        return ResponseEntity.ok(cartDto);
    }

    // 장바구니 생성 : 초기 데이터를 기반으로 장바구니 생성(USER 는 하나의 장바구니를 가진다.)
    @PostMapping
    public ResponseEntity<CartDto> createCart(
            @RequestBody CartDto cartDto,
            HttpServletResponse response
    ) {
        CartDto updatedCart = cartService.saveCart(response, cartDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCart);
    }

    // 장바구니 수정 : 기존 장바구니의 모든 데이터를 새 데이터로 변경
    @PutMapping
    public ResponseEntity<CartDto> updateCart(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody CartDto cartDto
    ) {
        CartDto updatedCart = cartService.updateCart(request, response, cartDto);
        return ResponseEntity.ok(updatedCart);
    }

    // 장바구니에 새로운 메뉴 추가 : 새로운 메뉴 추가 또는 기존 메뉴의 수량 증가
    @PostMapping("/item")
    public ResponseEntity<CartDto> addToCart(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody CartItemDto newItem
    ) {
        CartDto updatedCart = cartService.addToCart(request, response, newItem);
        return ResponseEntity.ok(updatedCart);
    }

    // 장바구니에서 주문 생성 : 장바구니 데이터를 기반으로 주문 생성
    @PostMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> createOrdersFromCart(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) {
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);
        List<OrderResponseDto> orders = cartService.createOrdersFromCart(loginUser.getUserId(), request, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(orders);
    }

    // 장바구니 삭제 : 장바구니 데이터를 초기화
    @DeleteMapping
    public ResponseEntity<Void> clearCart(HttpServletResponse response) {
        cartService.clearCart(response);
        return ResponseEntity.noContent().build();
    }
}