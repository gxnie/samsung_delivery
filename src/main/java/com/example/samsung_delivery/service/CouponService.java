package com.example.samsung_delivery.service;


import com.example.samsung_delivery.dto.coupon.CouponRequestDto;
import com.example.samsung_delivery.dto.coupon.CouponResponseDto;
import com.example.samsung_delivery.entity.Coupon;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.UserRole;
import com.example.samsung_delivery.error.errorcode.ErrorCode;
import com.example.samsung_delivery.error.exception.CustomException;
import com.example.samsung_delivery.repository.CouponRepository;
import com.example.samsung_delivery.repository.StoreRepository;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    //쿠폰생성
    @Transactional
    public CouponResponseDto createCoupon(Long userId , CouponRequestDto requestDto){

        User loginUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Store findStore = storeRepository.findById(requestDto.getStoreId()).orElseThrow(
                ()-> new CustomException(ErrorCode.STORE_NOT_FOUND));
        //요청유저가 owner 인지 판별
        if (!Objects.equals(loginUser.getRole(), UserRole.OWNER)){
            throw new CustomException(ErrorCode.INVALID_USER_ROLE);
        }
        //요청유저가 요청가게의 주인인지 판별
        if (!Objects.equals(loginUser.getId(), findStore.getUser().getId())){
            throw new CustomException(ErrorCode.INVALID_STORE_ACCESS);
        }
        //만료 기한 설정
        String localDate = requestDto.getExpiredAt().toString();
        LocalDateTime expiredAt =  LocalDateTime.parse(localDate+" 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Coupon coupon = new Coupon(requestDto.getDiscount(), requestDto.getMaxIssued() , expiredAt);
        coupon.setStore(findStore);
        coupon.setCouponType(requestDto.getCouponType());
        couponRepository.save(coupon);
        return new CouponResponseDto(coupon);
    }


}
