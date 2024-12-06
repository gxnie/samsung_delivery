package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.entity.Cart;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);

    Optional<Cart> findByUserAndStoreAndMenuName(User user, Store store, String menuName);

    Optional<Cart> findByIdAndUserId(Long id, Long userId);

}
