package com.example.samsung_delivery.dto.review;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    @NotNull(message = "주문 ID는 필수입니다.")
    private final Long orderId;

    @NotNull(message = "리뷰 내용은 필수입니다.")
    private final String content;

    // rating 값이 최소1, 최대5 범위 설정
    @NotNull(message = "평점은 필수입니다.")
    @Min(value = 1, message = "최소 평점은 1점입니다.")
    @Max(value = 5, message = "최대 평점은 5점입니다.")
    private final int rating;

    public ReviewRequestDto(Long orderId, String content, int rating) {
        this.orderId = orderId;
        this.content = content;
        this.rating = rating;
    }

}
