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
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final PointService pointService;


    //주문 생성
    @Transactional
    public OrderResponseDto save (Long userId , OrderRequestDto dto) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Menu findMenu = menuRepository.findById(dto.getMenuId()).orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
        Store findStore = findMenu.getStore();
        //총 주문 금액 (메뉴가격 * 수량 - 사용포인트)
        int totalPrice = (findMenu.getPrice() * dto.getQuantity()) - dto.getUsePoint();
        int savePoint = totalPrice * 3/100;

        if (findStore.getMinOrderPrice() > totalPrice) {
            throw new CustomException(ErrorCode.PRICE_NOT_ENOUGH);
        }

        Order order = new Order(dto.getQuantity() , dto.getUsePoint() , totalPrice , dto.getAddress());
        order.setUser(findUser);
        order.setMenu(findMenu);
        order.setStatus(OrderStatus.ORDER_COMPLETED);

        //사용포인트가 있을경우
        if(dto.getUsePoint() != 0 ){
            pointService.usePoint(userId , dto.getUsePoint());
        }
        //포인트 적립
        pointService.savePoint(userId,savePoint);
        int remainPoint = pointService.getUserTotalPoint(userId);
        orderRepository.save(order);

        return new OrderResponseDto(order ,remainPoint);
    }

    //주문 상태값 변경
    @Transactional
    public OrderResponseDto changeOrderStatus (Long orderId , String status){
        Order findOrder = findOderById(orderId);
        switch (status){
            case "ACCEPT_ORDER": findOrder.updateStatus(OrderStatus.ACCEPT_ORDER);
                break;
            case "COOKING": findOrder.updateStatus(OrderStatus.COOKING);
                break;
            case "COOKING_COMPLETED": findOrder.updateStatus(OrderStatus.COOKING_COMPLETED);
                break;
            case "ON_DELIVERY": findOrder.updateStatus(OrderStatus.ON_DELIVERY);
                break;
            case "DELIVERY_COMPLETED": findOrder.updateStatus(OrderStatus.DELIVERY_COMPLETED);
                break;
        }
        return new OrderResponseDto(findOrder);
    }

    public Order findOderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

}
