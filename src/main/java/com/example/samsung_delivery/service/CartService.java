package com.example.samsung_delivery.service;

import com.example.samsung_delivery.entity.Cart;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.repository.CartRepository;
import com.example.samsung_delivery.repository.StoreRepository;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final HttpServletResponse response;

    // 장바구니 생성
    @Transactional
    public List<Cart> addToCart(Long userId, Long storeId, String menuName, int quantity, int price) {
        // 어떻게 체크 할것인가? -> DB조회시 조건들을 넣음
        List<Cart> cartList = cartRepository.findByUserId(userId);
        //체크한 파일이 없으면 생성 있으면 있는거 사용
        if(cartList.size() == 0 ){
            User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
            Store store = storeRepository.findById(storeId).orElseThrow(EntityNotFoundException::new);

            // 새로운 장바구니 생성
            Cart cart = new Cart(user,store,menuName,quantity,price);
            cartRepository.save(cart);
            setCartCookie();
            return List.of(cart);
        } else {
            // 장바구니에 이미 메뉴가 있는 경우
            Cart existingCart = cartList.get(0);
            if (!existingCart.getStore().getId().equals(storeId)) {

                // 다른 가게의 메뉴를 추가하려고 할 때, 장바구니 초기화
                cartRepository.deleteAll(cartList);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                throw new IllegalArgumentException("다른 가게의 메뉴를 추가할 수 없습니다."); // CustomException 으로 수정
            }

            // 동일한 가게일 경우 장바구니 업데이트
            existingCart.updateCart(menuName,quantity,price);

            cartRepository.save(existingCart);
            return cartRepository.findByUserId(userId);
        }
    }

    // 하루가 지난 장바구니 제거 Get

//    @Scheduled(cron = "0 0 0 * * ?")
//    public void clearOldCarts() {
//        Date oneDayAgo = Date.from(Instant.now().minus(1, ChronoUnit.DAYS));
//
//        List<Cart> oldCarts = cartRepository.findAll().stream()
//                .filter(cart -> cart.getLastUpdated().before(oneDayAgo))
//                .toList();
//
//        cartRepository.deleteAll(oldCarts);
//    }

    // 장바구니 유효성 검사 후 쿠키 설정
    private void setCartCookie() {
        Cookie cartCookie = new Cookie("cart_valid", "true");
        cartCookie.setMaxAge(24 * 60 * 60); // 1 day
        cartCookie.setPath("/");
        response.addCookie(cartCookie);
    }


}
