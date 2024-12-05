package com.example.samsung_delivery.service;

import com.example.samsung_delivery.dto.review.ReviewResponseDto;
import com.example.samsung_delivery.entity.*;
import com.example.samsung_delivery.enums.OrderStatus;
import com.example.samsung_delivery.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository  ;
    private final StoreRepository storeRepository;

    public ReviewResponseDto saveReview(Long userId, Long orderId, String content, int rating) {
        // 주문 조회
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        // 배달 상태 검증
        if (findOrder.getStatus() != OrderStatus.DELIVERY_COMPLETED) {
            throw new IllegalArgumentException("배달이 완료되지 않은 주문에는 리뷰를 작성할 수 없습니다.");
        }

        // id값 찾기(유저id, 주문의 해당 메뉴id, 주문한 가게의 id) NoSuchElementException
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Menu findMenu = menuRepository.findById(findOrder.getMenu().getId()).orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));
        Store findStore = storeRepository.findById(findOrder.getId()).orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다."));


        // 리뷰 생성 및 저장(id값을 저장)
        Review review = new Review(findUser, findOrder, findStore, findMenu, content, rating);
        reviewRepository.save(review);

        // 응답 dto 반환
        return new ReviewResponseDto(
                review.getId(),
                findUser.getId(),
                findOrder.getId(),
                findStore.getId(),
                findMenu.getId(),
                content,
                rating,
                review.getCreatedAt(),
                review.getModifiedAt()
        );
    }



    // 리뷰 조회
    @Transactional
    public List<ReviewResponseDto> getReviews(Long storeId, Integer minRating, Integer maxRating, Long userId) {
        List<Review> reviews = reviewRepository.findReviewsByStore(storeId, minRating, maxRating, userId);

        return reviews.stream()
                .map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getUser().getId(),
                        review.getOrder().getId(),
                        review.getStore().getId(),
                        review.getMenu().getId(),
                        review.getContent(),
                        review.getRating(),
                        review.getCreatedAt(),
                        review.getModifiedAt()
                ))
                .collect(Collectors.toList());
    }


}

