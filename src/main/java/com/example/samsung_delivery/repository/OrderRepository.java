package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
