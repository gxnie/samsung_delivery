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

        // 사용자 확인 : email 을 통해 사용자를 조회. 없을 경우 예외 처리
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 사용자 권환 확인 : 반드시 OWNER (가게 관리자)이어야 함
        if (user.getRole() != UserRole.OWNER) {
            throw new CustomException(ErrorCode.INVALID_USER_ROLE);
        }

        // 가게 정보 확인 : storeId 로 가게를 조회. 없을 경우 예외 처리
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        // 가게 소유자가 현재 사용자와 동일한지 확인
        if (!store.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.INVALID_USER_ROLE);
        }

        // 메뉴 객체 생성 및 데이터 베이스 저장 : 기본적으로 상태는 AVAILABLE
        Menu menu = new Menu(menuName, price, store, MenuStatus.AVAILABLE);
        Menu savedMenu = menuRepository.save(menu);

        return new MenuResponseDto(savedMenu.getId(), store.getId(), savedMenu.getMenuName(), savedMenu.getPrice());

    }

    // 메뉴 수정
    @Transactional
    public MenuResponseDto updateMenu(Long menuId, String email, String menuName, int price) {

        // 사용자 확인
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 사용자 권환 확인
        if (user.getRole() != UserRole.OWNER) {
            throw new CustomException(ErrorCode.INVALID_USER_ROLE);
        }

        // 메뉴 정보 확인
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        // 메뉴가 속한 가게가 현재 사용자의 소유인지 확인
        if (!menu.getStore().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.INVALID_STORE_ACCESS);
        }

        // 메뉴 정보 수정
        menu.updateMenu(menuName, price);
        return new MenuResponseDto(menuRepository.save(menu));

    }

    // 메뉴 삭제 : 상태를 CLOSE 로 변경
    @Transactional
    public void deleteMenu(Long menuId, String email) {

        // 사용자 정보 확인
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 사용자 권환 확인
        if (user.getRole() != UserRole.OWNER) {
            throw new CustomException(ErrorCode.INVALID_USER_ROLE);
        }

        // 메뉴 정보 확인
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        // 메뉴가 속한 가게가 현재 사용자의 소유인지 확인
        if (!menu.getStore().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.INVALID_STORE_ACCESS);
        }

        // 메뉴 상태를 OUT_OF_STOCK 로 업데이트
        menuRepository.updateStatus(menuId, MenuStatus.OUT_OF_STOCK);
    }
}
