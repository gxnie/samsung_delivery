package com.example.samsung_delivery.service;

import com.example.samsung_delivery.dto.order.OrderRequestDto;
import com.example.samsung_delivery.dto.order.OrderResponseDto;
import com.example.samsung_delivery.dto.order.OrderUseCouponResponseDto;
import com.example.samsung_delivery.entity.*;
import com.example.samsung_delivery.enums.CouponType;
import com.example.samsung_delivery.enums.OrderStatus;
import com.example.samsung_delivery.error.errorcode.ErrorCode;
import com.example.samsung_delivery.error.exception.CustomException;
import com.example.samsung_delivery.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final PointService pointService;
    private final CouponRepository couponRepository;
    private final CouponService couponService;


    //주문 생성
    @Transactional
    public OrderResponseDto createUsePointOrder(Long userId , OrderRequestDto dto) {
        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Menu findMenu = menuRepository.findById(dto.getMenuId()).orElseThrow(
                () -> new CustomException(ErrorCode.MENU_NOT_FOUND));
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

    public OrderUseCouponResponseDto createUseCouponOrder(Long userId ,Long couponId ,OrderRequestDto dto){

        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Menu findMenu = menuRepository.findById(dto.getMenuId()).orElseThrow(
                () -> new CustomException(ErrorCode.MENU_NOT_FOUND));
        Store findStore = findMenu.getStore();
        Coupon findCoupon = couponRepository.findById(couponId).orElseThrow(
                ()->new CustomException(ErrorCode.COUPON_NOT_FOUND));

        int price = findMenu.getPrice() * dto.getQuantity() - dto.getUsePoint();
        int totalPrice = getTotalPrice(price,findCoupon);
        int savePoint = totalPrice * 3/100;

        if (findStore.getMinOrderPrice() > totalPrice) {
            throw new CustomException(ErrorCode.PRICE_NOT_ENOUGH);
        }
        Order order = new Order(dto.getQuantity() , dto.getUsePoint() , totalPrice , dto.getAddress());
        order.setUser(findUser);
        order.setMenu(findMenu);
        order.setCoupon(findCoupon);
        order.setStatus(OrderStatus.ORDER_COMPLETED);

        //사용포인트가 있을경우
        if(dto.getUsePoint() != 0 ){
            pointService.usePoint(userId , dto.getUsePoint());
        }
        //포인트 적립
        pointService.savePoint(userId,savePoint);
        int remainPoint = pointService.getUserTotalPoint(userId);
        orderRepository.save(order);
        //쿠폰사용 로직
        couponService.useCoupon(userId,couponId,findStore.getId());

        return new OrderUseCouponResponseDto(order,remainPoint,couponId);
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

    public int getTotalPrice(int price ,Coupon coupon){
        if (coupon.getCouponType() == CouponType.FIXED_AMOUNT){
            return price - coupon.getDiscount();
        }
        if (coupon.getCouponType() == CouponType.FIXED_RATE){
            return price*(100-coupon.getDiscount())/100;
        }
        return 0;
    }

}
