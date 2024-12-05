package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.Menu;
import com.example.samsung_delivery.enums.MenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Modifying
    @Query("update Menu m set m.status = :status where m.id = :menuId")
    void updateStatus(@Param("menuId") Long menuId, @Param("status") MenuStatus status);

    List<Menu> findByStoreAndStatus(Store store);
}
