package com.example.samsung_delivery.config;

import com.example.samsung_delivery.enums.PointStatus;
import com.example.samsung_delivery.repository.PointRepository;
import com.example.samsung_delivery.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class PointScheduler {

    private final PointService pointService;
//  (cron = "0 0 00 * * *") 매일 00시
//  (cron = "0/3 * * * * ?") 3초마다
    @Scheduled(cron = "0 0 00 * * *")
    public void pointStatusUpdate(){
        DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        System.out.println("스케쥴러 테스트 " + LocalDateTime.now().format(dtf));
        pointService.statusUpdate();
    }
}
