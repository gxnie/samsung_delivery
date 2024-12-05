package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.dto.cart.CartRequestDto;
import com.example.samsung_delivery.entity.Cart;
import com.example.samsung_delivery.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    @PostMapping
    public ResponseEntity<List<Cart>> createCart(@RequestBody CartRequestDto requestDto) {
        try {
            List<Cart> carts = cartService.addToCart(requestDto.getUserId(), requestDto.getStoreId(),
                    requestDto.getMenuName(), requestDto.getQuantity(), requestDto.getPrice());
            return new ResponseEntity<>(carts, HttpStatus.OK);
        } catch (IllegalAccessException exception) { // CustomException 으로 수정
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}