package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.dto.cart.CartDto;
import com.example.samsung_delivery.dto.cart.CartItemDto;
import com.example.samsung_delivery.entity.Cart;
import com.example.samsung_delivery.service.CartService;
import jakarta.servlet.http.HttpServletResponse;
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

    @GetMapping
    public ResponseEntity<List<CartDto>> getCart(@CookieValue(name = "cart", required = false) String cartCookie) {
        List<CartDto> cartList = cartService.getCartList(cartCookie);
        return ResponseEntity.ok(cartList);
    }

    @PostMapping
    public ResponseEntity<Void> addToCart(
            @CookieValue(name = "cart", required = false) String cartCookie,
            @RequestBody CartItemDto newItem,
            @RequestParam Long userId,
            @RequestParam Long storeId,
            HttpServletResponse response
    ) {
        List<CartDto> updatedCartList = cartService.addToCart(cartCookie, userId, storeId, newItem);
        cartService.setCartCookie(response, updatedCartList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(HttpServletResponse response) {
        cartService.clearCartCookie(response);
        return ResponseEntity.ok().build();
    }



//    // 장바구니 생성 전 데이터가 남아있는지 확인을 위한 조회
//    @GetMapping
//    public ResponseEntity<List<CartResponseDto>> getCart(@RequestParam Long userId) {
//        List<CartResponseDto> carts = cartService.getCart(userId);
//        return new ResponseEntity<>(carts, HttpStatus.OK);
//    }
//
//    // 장바구니 생성
//    @PostMapping
//    public ResponseEntity<List<Cart>> createCart(@RequestBody CartRequestDto requestDto) {
//        List<Cart> carts = cartService.addToCart(
//                requestDto.getUserId(), requestDto.getStoreId(),
//                requestDto.getMenuName(), requestDto.getQuantity(), requestDto.getPrice()
//        );
//        List<CartResponseDto> responseDto = carts.stream()
//                .map(cart -> new CartResponseDto(cart.getId(), cart.getMenuName(), cart.getPrice(), cart.getQuantity()))
//                .toList();
//        return new ResponseEntity<>(carts, HttpStatus.CREATED);
//    }
//
//    // 장바구니 수정
//    @PutMapping
//    public ResponseEntity<List<CartResponseDto>> updateCart(@RequestBody CartRequestDto requestDto) {
//        List<CartResponseDto> carts = cartService.updateCart(
//                requestDto.getUserId(),
//                requestDto.getMenuName(),
//                requestDto.getQuantity(),
//                requestDto.getPrice()
//        );
//        return new ResponseEntity<>(carts, HttpStatus.OK);
//    }


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