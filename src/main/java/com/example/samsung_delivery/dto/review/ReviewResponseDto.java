package com.example.samsung_delivery.dto.review;

import com.example.samsung_delivery.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponseDto {

    private final Long id;

    private final Long userId;

    private final Long orderId;

    private final Long storeId;

    private final Long menuId;

    private final String content;

    private final int rating;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public ReviewResponseDto(Long id, Long userId, Long orderId, Long storeId, Long menuId, String content,
                             int rating, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.storeId = storeId;
        this.menuId = menuId;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }


/**
 *  {
 *   “reviewId” : 1,
 *   “userId” : 1,
 *   “orderId” : 1,
 *   “storeId” : 1,
 *   “menuId” : 1,
 *   “content” : “음식이 친절해요”,
 *   “rating” : 5
 * }
 *  **/


}
