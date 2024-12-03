package com.example.samsung_delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menu")
@Getter
public class Menu extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Setter
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String status;

    public Menu(Long id, Store store, Order order, String menuName, int price, String status) {
        this.id = id;
        this.store = store;
        this.order = order;
        this.menuName = menuName;
        this.price = price;
        this.status = status;
    }

    public Menu() {}
}
