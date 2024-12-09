package com.example.samsung_delivery.entity;

import com.example.samsung_delivery.enums.PointStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "`point`")
public class Point extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int point;

    private LocalDateTime expiredAt;

    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PointStatus status;

    public Point(){}

    public Point(Integer point , LocalDateTime expiredAt){
        this.point = point;
        this.expiredAt = expiredAt;
    }

    public void usePoint(Integer point){
        this.point = point;
    }

}
