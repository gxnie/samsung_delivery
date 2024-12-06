package com.example.samsung_delivery.controller;


import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.coupon.CouponRequestDto;
import com.example.samsung_delivery.dto.coupon.CouponResponseDto;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.service.CouponService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponResponseDto> createCoupon(
            @RequestBody CouponRequestDto requestDto,
            HttpServletRequest httpServletRequest
            ){
        HttpSession session = httpServletRequest.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);

        return new ResponseEntity<>(couponService.createCoupon(loginUser.getUserId(), requestDto), HttpStatus.OK);
    }
}
