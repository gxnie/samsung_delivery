package com.example.samsung_delivery.service;

import com.example.samsung_delivery.dto.order.OrderRequestDto;
import com.example.samsung_delivery.dto.order.OrderResponseDto;
import com.example.samsung_delivery.entity.Menu;
import com.example.samsung_delivery.entity.Order;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.OrderStatus;
import com.example.samsung_delivery.error.errorcode.ErrorCode;
import com.example.samsung_delivery.error.exception.CustomException;
import com.example.samsung_delivery.repository.MenuRepository;
import com.example.samsung_delivery.repository.OrderRepository;
import com.example.samsung_delivery.repository.StoreRepository;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository  ;
    private final StoreRepository storeRepository;

    @Transactional
    public OrderResponseDto save (Long userId , Long menuId, Integer quantity , String address ) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        Menu findMenu = menuRepository.findById(menuId).orElseThrow(() -> new NoSuchElementException("메뉴를 찾을 수 없습니다."));
        Store findStore = findMenu.getStore();
        int totalPrice = findMenu.getPrice() * quantity;

        if (findStore.getMinOrderPrice() > totalPrice) {
            throw new CustomException(ErrorCode.PRICE_NOT_ENOUGH);
        }

        Order order = new Order(quantity , totalPrice , address);
        order.setUser(findUser);
        order.setMenu(findMenu);
        order.setStatus(OrderStatus.ACCEPT_ODER);

        orderRepository.save(order);

        return new OrderResponseDto(order);

    }

}
