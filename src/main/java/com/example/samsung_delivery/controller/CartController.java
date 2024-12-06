package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.dto.cart.CartRequestDto;
import com.example.samsung_delivery.dto.cart.CartResponseDto;
import com.example.samsung_delivery.entity.Cart;
import com.example.samsung_delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    // 장바구니 생성 전 데이터가 남아있는지 확인을 위한 조회
    @GetMapping
    public ResponseEntity<List<CartResponseDto>> getCart(@RequestParam Long userId) {
        List<CartResponseDto> carts = cartService.getCart(userId);
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    // 장바구니 생성
    @PostMapping
    public ResponseEntity<List<Cart>> createCart(@RequestBody CartRequestDto requestDto) {
        List<Cart> carts = cartService.addToCart(
                requestDto.getUserId(), requestDto.getStoreId(),
                requestDto.getMenuName(), requestDto.getQuantity(), requestDto.getPrice()
        );
        List<CartResponseDto> responseDto = carts.stream()
                .map(cart -> new CartResponseDto(cart.getId(), cart.getMenuName(), cart.getPrice(), cart.getQuantity()))
                .toList();
        return new ResponseEntity<>(carts, HttpStatus.CREATED);
    }

    // 장바구니 수정
    @PutMapping
    public ResponseEntity<List<CartResponseDto>> updateCart(@RequestBody CartRequestDto requestDto) {
        List<CartResponseDto> carts = cartService.updateCart(
                requestDto.getUserId(),
                requestDto.getMenuName(),
                requestDto.getQuantity(),
                requestDto.getPrice()
        );
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }


//    // 장바구니 생성
//    @PostMapping
//    public ResponseEntity<List<CartResponseDto>> createCart(@RequestBody CartRequestDto requestDto) {
//        List<CartResponseDto> carts = cartService.addToCart(
//                requestDto.getUserId(), requestDto.getStoreId(),
//                requestDto.getMenuName(), requestDto.getQuantity(), requestDto.getPrice()
//        );
//        return new ResponseEntity<>(carts, HttpStatus.CREATED);
//    }


    // 장바구니 수정
//    @PutMapping
//    public ResponseEntity<CartResponseDto> updateCart(@RequestBody CartRequestDto requestDto) {
//        try {
//            List<CartResponseDto> responseDto = carts.stream()
//                    .map(cart -> new CartResponseDto(cart.getId(), cart.getMenuName(), cart.getPrice(), cart.getQuantity()))
//                    .toList();
//            return new ResponseEntity<>(responseDto, HttpStatus.OK);
//        } catch (IllegalAccessException exception) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
}