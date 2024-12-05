package com.example.samsung_delivery.controller;


import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.review.ReviewRequestDto;
import com.example.samsung_delivery.dto.review.ReviewResponseDto;
import com.example.samsung_delivery.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
     @PostMapping
     public ResponseEntity<ReviewResponseDto>  createReview(
             @Valid @RequestBody ReviewRequestDto requestDto,
             HttpServletRequest httpServletRequest
     ) {

         // 세션에서 로그인된 사용자 정보 가져오기
         HttpSession session = httpServletRequest.getSession(false);
         LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);

         // 리뷰 저장
         ReviewResponseDto responseDto = reviewService.saveReview(
                 loginUser.getUserId(),
                 requestDto.getOrderId(),
                 requestDto.getContent(),
                 requestDto.getRating()
         );

         return ResponseEntity.ok(responseDto);
     }



    // 리뷰 조회
    // reviews?storeId=1&sort=rating
    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getReviews(
            @RequestParam Long storeId,
            @RequestParam(required = false, defaultValue = "1") Integer minRating,
            @RequestParam(required = false, defaultValue = "5") Integer maxRating,
            HttpServletRequest request) {

        // 로그인된 사용자 ID 가져오기
        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);
        Long userId = loginUser.getUserId();

        // 가게 id, 최소 별점, 최대 별점, 로그인된 id reviewService로 전달
        // 리뷰 리스트 조회
        List<ReviewResponseDto> review = reviewService.getReviews(storeId, minRating, maxRating, userId);

        return ResponseEntity.ok(review);
    }


}
