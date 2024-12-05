package com.example.samsung_delivery.service;

import com.example.samsung_delivery.dto.menu.MenuResponseDto;
import com.example.samsung_delivery.entity.Menu;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.MenuStatus;
import com.example.samsung_delivery.enums.UserRole;
import com.example.samsung_delivery.error.errorcode.ErrorCode;
import com.example.samsung_delivery.error.exception.CustomException;
import com.example.samsung_delivery.repository.MenuRepository;
import com.example.samsung_delivery.repository.StoreRepository;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    // 메뉴 생성
    @Transactional
    public MenuResponseDto createMenu(Long storeId, String email, String menuName, int price) {

        // 사용자 확인
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user.getRole() != UserRole.OWNER) {
            throw new CustomException(ErrorCode.INVALID_USER_ROLE);
        }

        // 가게 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        if (!store.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.INVALID_USER_ROLE);
        }

        Menu menu = new Menu(menuName, price, store, MenuStatus.OPEN);
        Menu savedMenu = menuRepository.save(menu);

        return new MenuResponseDto(savedMenu.getId(), store.getId(), savedMenu.getMenuName(), savedMenu.getPrice());

    }

    // 메뉴 수정
    @Transactional
    public MenuResponseDto updateMenu(Long menuId, String email, String menuName, int price) {

        // 사용자 확인
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user.getRole() != UserRole.OWNER) {
            throw new CustomException(ErrorCode.INVALID_USER_ROLE);
        }

        // 메뉴 확인
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        if (!menu.getStore().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.INVALID_STORE_ACCESS);
        }

        menu.updateMenu(menuName, price);
        return new MenuResponseDto(menuRepository.save(menu));

    }

    // 메뉴 삭제
    @Transactional
    public void deleteMenu(Long menuId, String email) {
        // 사용자 확인
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user.getRole() != UserRole.OWNER) {
            throw new CustomException(ErrorCode.INVALID_USER_ROLE);
        }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        if (!menu.getStore().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.INVALID_STORE_ACCESS);
        }

        // 상태 변경
        menuRepository.updateStatus(menuId, MenuStatus.CLOSE);
    }
}
