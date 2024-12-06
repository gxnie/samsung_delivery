//package com.example.samsung_delivery.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Getter;
//
//@Entity
//@Table(name = "Cart")
//@Getter
//public class Cart extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "store_id", nullable = false)
//    private Store store;
//
//    @Column(nullable = false)
//    private String menuName;
//
//    @Column(nullable = false)
//    private int quantity;
//
//    @Column(nullable = false)
//    private int price;
//
////    // 장바구니 기본 값 true
////    @Column(nullable = false)
////    private boolean isVisible = true;
////
////    // 장바구니 숨기기
////    public void hideCart() {
////        this.isVisible = false;
////    }
//
//    public void updateCart(String menuName, int quantity, int price) {
//        this.menuName = menuName;
//        this.quantity = quantity;
//        this.price = price;
//    }
//
//    public Cart() {}
//
//    public Cart(User user, Store store, String menName, int quantity, int price) {
//        this.user = user;
//        this.store = store;
//        this.menuName = menName;
//        this.quantity = quantity;
//        this.price = price;
//    }
//}
