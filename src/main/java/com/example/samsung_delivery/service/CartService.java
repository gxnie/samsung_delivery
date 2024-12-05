package com.example.samsung_delivery.service;

import com.example.samsung_delivery.entity.Cart;
import com.example.samsung_delivery.repository.CartRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private CartRepository cartRepository;
    private HttpServletResponse response;

    public List<Cart> addToCart(Long userId, Long storeId, String menuName, int quantity, int price) {
        List<Cart> cartList = cartRepository.findByUserId(userId);

//        if (cartList.isEmpty()) {
//            Cart cart = new Cart();
//            cart.setUserId(userId);
//            cart.setStoreId(storeId);
//            cart.setMenuName(menuName);
//            cart.setQuantity(quantity);
//            cart.setPrice(price);
//        }
    }
}
