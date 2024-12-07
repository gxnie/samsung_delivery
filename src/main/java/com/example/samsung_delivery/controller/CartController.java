package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.dto.cart.CartDto;
import com.example.samsung_delivery.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @GetMapping
    public ResponseEntity<CartDto> getCart(HttpServletRequest request) {
        CartDto cartDto = cartService.getCart(request);
        return ResponseEntity.ok(cartDto);
    }

    // 장바구니 생성
    @PostMapping
    public ResponseEntity<CartDto> createCart(
            @RequestBody CartDto cartDto,
            HttpServletResponse response
    ) {
        CartDto updatedCart = cartService.saveCart(response, cartDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCart);
    }

    @PutMapping
    public ResponseEntity<CartDto> updateCart(
            @RequestBody CartDto cartDto,
            HttpServletResponse response
    ) {
        CartDto updatedCart = cartService.saveCart(response, cartDto);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(HttpServletResponse response) {
        cartService.clearCart(response);
        return ResponseEntity.noContent().build();
    }
}