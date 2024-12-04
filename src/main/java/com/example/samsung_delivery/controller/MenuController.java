package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.menu.MenuRequestDto;
import com.example.samsung_delivery.dto.menu.MenuResponseDto;
import com.example.samsung_delivery.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuResponseDto> createMenu(
            @RequestBody MenuRequestDto dto,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);

        MenuResponseDto menuResponseDto = menuService.createMenu(dto.getStoreId(), loginUser.getEmail(), dto.getMenuName(), dto.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(menuResponseDto);
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<Void> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuRequestDto dto,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);

        menuService.updateMenu(menuId, loginUser.getEmail(), dto.getMenuName(), dto.getPrice());
        return ResponseEntity.ok().build();

    }

}

