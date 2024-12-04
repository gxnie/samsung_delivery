package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

//    @Query("select count(*)" +
//            "from Store st "+
//            "where st.user.id = :userId ")
//    Optional<Long> findTotalStoreCount(@Param("userId") String userId);
    Long countByUserIdAndStatus(Long userId, StoreStatus status);

}
