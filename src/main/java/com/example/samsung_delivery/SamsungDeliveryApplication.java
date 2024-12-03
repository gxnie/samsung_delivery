package com.example.samsung_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class SamsungDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SamsungDeliveryApplication.class, args);
    }

}
