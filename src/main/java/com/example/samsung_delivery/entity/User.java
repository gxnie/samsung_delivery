package com.example.samsung_delivery.entity;


import com.example.samsung_delivery.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // USER, OWNER
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Store> store = new ArrayList<>();

    public User(){}


    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
