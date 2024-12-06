package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.entity.Point;
import com.example.samsung_delivery.enums.PointStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point,Long> {


    @Query(value = "select sum (p.point) as totalPoint from Point as p where p.status = 'AVAILABLE' and p.user.id = :userId ")
    int totalPoint(@Param("userId") Long userId);

    @Modifying
    @Query(value = "update Point p set p.status = :status where p.expiredAt < now()")
    void updateStatus(@Param("status") PointStatus status);

    Optional<Point> findByUser_Id(Long userId);
}
