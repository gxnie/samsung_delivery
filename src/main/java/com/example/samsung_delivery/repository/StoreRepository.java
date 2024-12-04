package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StoreRepository extends JpaRepository<Store, Long> {
    Long countByUserIdAndStatus(Long userId, StoreStatus status);


    @Modifying
    @Query("update Store s set s.status = :status where s.id = :storeId")
    void updateStatus(Long storeId, StoreStatus status);
    List<Store> findByStatus(StoreStatus status);

}
