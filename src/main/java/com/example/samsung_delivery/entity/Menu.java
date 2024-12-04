package com.example.samsung_delivery.entity;

import com.example.samsung_delivery.enums.MenuStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "menu")
@Getter
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuStatus status = MenuStatus.OPEN;

    public Menu(String menuName, int price, Store store, MenuStatus status) {
        this.menuName = menuName;
        this.price = price;
        this.store = store;
        this.status = status;
    }

    public Menu() {}

    public void updateMenu(String menuName, int price) {
        this.menuName = menuName;
        this.price = price;
    }
}
