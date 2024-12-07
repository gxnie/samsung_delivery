package com.example.samsung_delivery.controller;

import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.menu.MenuRequestDto;
import com.example.samsung_delivery.dto.menu.MenuResponseDto;
import com.example.samsung_delivery.error.errorcode.ErrorCode;
import com.example.samsung_delivery.error.exception.CustomException;
import com.example.samsung_delivery.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    // 메뉴 생성
    @PostMapping
    public ResponseEntity<MenuResponseDto> createMenu(
            @RequestBody MenuRequestDto dto,
            HttpServletRequest request) {

        // 현재 사용자의 세션 가져오기
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }

        // 세션에서 로그인 된 사용자 정보 가져오기
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);

        MenuResponseDto menuResponseDto = menuService.createMenu(dto.getStoreId(), loginUser.getEmail(), dto.getMenuName(), dto.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(menuResponseDto);
    }

    // 메뉴 수정
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(
            @PathVariable Long menuId,
            @RequestBody MenuRequestDto dto,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }

        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);

        MenuResponseDto menuResponseDto = menuService.updateMenu(menuId, loginUser.getEmail(), dto.getMenuName(), dto.getPrice());
        return new ResponseEntity<>(menuResponseDto, HttpStatus.OK);

    }

    // 메뉴 삭제
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @PathVariable Long menuId,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }

        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);

        // MenuService 를 호출해 메뉴 상태를 CLOSE 로 변경
        menuService.deleteMenu(menuId, loginUser.getEmail());
        return ResponseEntity.ok().build();
    }

}

