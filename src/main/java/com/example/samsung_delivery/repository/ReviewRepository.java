package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * Review 엔티티와 매핑된 테이블에서 모든 데이터를 가져오고, List<Review> 형태로 반환
     * (SELECT r FROM Review r)
     * 가게 기준으로 리뷰 조회 (WHERE r.store.id = :storeId)
     * 본인 리뷰 제외 (AND r.user.id != :userId)
     * 별점 범위 조회 (AND r.rating BETWEEN :minRating AND :maxRating)
     * 생성일자 기준 최신순 (ORDER BY r.createdAt DESC)
      */

    @Query("SELECT r FROM Review r " +
            "WHERE r.store.id = :storeId " +
            "AND r.rating BETWEEN :minRating AND :maxRating " +
            "AND r.user.id != :userId " +
            "ORDER BY r.createdAt DESC")

    List<Review> findReviewsByStore(
            @Param("storeId") Long storeId,
            @Param("minRating") Integer minRating,
            @Param("maxRating") Integer maxRating,
            @Param("userId") Long userId
    );
    

}
