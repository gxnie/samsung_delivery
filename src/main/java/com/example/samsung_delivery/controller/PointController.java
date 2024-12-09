package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;


    @GetMapping("/{id}")
    public int getPoint(@PathVariable Long id){
        return pointService.getUserTotalPoint(id);
    }
}
