package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Long countByUserIdAndStatus(Long userId, StoreStatus status);

}
