package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
