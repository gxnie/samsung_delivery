package com.example.samsung_delivery.service;


import com.example.samsung_delivery.dto.coupon.CouponHistoryRequestDto;
import com.example.samsung_delivery.dto.coupon.CouponHistoryResponseDto;
import com.example.samsung_delivery.entity.Coupon;
import com.example.samsung_delivery.entity.CouponHistory;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.CouponStatus;
import com.example.samsung_delivery.error.errorcode.ErrorCode;
import com.example.samsung_delivery.error.exception.CustomException;
import com.example.samsung_delivery.repository.CouponHistoryRepository;
import com.example.samsung_delivery.repository.CouponRepository;
import com.example.samsung_delivery.repository.StoreRepository;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponHistoryService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final CouponRepository couponRepository;
    private final CouponHistoryRepository couponHistoryRepository;


    //쿠폰 발급
    public CouponHistoryResponseDto couponIssue(Long longinUserId , CouponHistoryRequestDto requestDto){
        User loginUser = userRepository.findById(longinUserId).orElseThrow(
                ()->new CustomException(ErrorCode.USER_NOT_FOUND));
        User findUser = userRepository.findById(requestDto.getUserId()).orElseThrow(
                ()->new CustomException(ErrorCode.USER_NOT_FOUND));
        Store findStore = storeRepository.findById(requestDto.getStoreId()).orElseThrow(
                ()-> new CustomException(ErrorCode.STORE_NOT_FOUND));
        Coupon findCoupon = couponRepository.findById(requestDto.getCouponId()).orElseThrow(
                ()-> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        //쿠폰을 발급해주는 유저가 해당쿠폰의 가게 주인인지 확인
        if (loginUser.getId() != findStore.getUser().getId()){
            throw new CustomException(ErrorCode.INVALID_STORE_ACCESS);
        }
        //발급받을 유저가 해당쿠폰을 발급받았는지 확인
        if (countUserCoupon(requestDto.getUserId(),requestDto.getCouponId()) != 0){
            throw new CustomException(ErrorCode.INVALID_COUPON_ISSUED);
        }
        //해당쿠폰 최대 발급수를 초과하는지 확인
        if (countCoupon(requestDto.getCouponId()) >= findCoupon.getMaxIssued()){
            throw new CustomException(ErrorCode.COUPON_CANNOT_ISSUED);
        }
        CouponHistory couponHistory = new CouponHistory(findUser,findStore,findCoupon);
        couponHistory.setStatus(CouponStatus.AVAILABLE);
        couponHistoryRepository.save(couponHistory);

        return new CouponHistoryResponseDto(couponHistory);
    }

    @Transactional
    public void useCoupon(Long userId , Long couponId , Long storedId){
        Optional<CouponHistory> couponHistoryOptional = couponHistoryRepository.findByUser_IdAndCoupon_Id(userId,couponId);
        int countIssuedCoupon = (int)couponHistoryRepository.countByUser_IdAndCoupon_Id(userId,couponId);
        //발급받은 쿠폰이 있는지 확인
        if (countIssuedCoupon == 0){
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
        }
        CouponHistory issuedCoupon = couponHistoryOptional.get();

        //사용하려는 쿠폰과 적용할 상점이 맞는지 확인
        if (!Objects.equals(issuedCoupon.getStore().getId(), storedId)){
            throw new CustomException(ErrorCode.INVALID_COUPON_STORE);
        }
        //사용가능한 상태의 쿠폰인지 확인
        if (!Objects.equals(issuedCoupon.getStatus(),CouponStatus.AVAILABLE)){
            throw new CustomException(ErrorCode.INVALID_COUPON_ACCESS);
        }
        issuedCoupon.setStatus(CouponStatus.USED);
    }





    public int countUserCoupon(Long userId , Long couponId){
        return (int)couponHistoryRepository.countByUser_IdAndCoupon_Id(userId,couponId);
    }

    public int countCoupon(Long couponId){
        return (int)couponHistoryRepository.countByCoupon_Id(couponId);
    }
}
