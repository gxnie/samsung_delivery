package com.example.samsung_delivery.entity;

import com.example.samsung_delivery.dto.review.ReviewRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
@Entity
@Table(name = "review")
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating;

    public Review(User user, Order order, Store store, Menu menu, String content, int rating) {
        this.user = user;
        this.order = order;
        this.store = store;
        this.menu = menu;
        this.content = content;
        this.rating = rating;
    }

    public Review(){}

}
