package com.example.samsung_delivery.service;

import com.example.samsung_delivery.dto.menu.MenuResponseDto;
import com.example.samsung_delivery.entity.Menu;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.MenuStatus;
import com.example.samsung_delivery.enums.UserRole;
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
        User user = userRepository.findUserByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        if (user.getRole() != UserRole.OWNER) {
            throw new IllegalArgumentException("사장님만 메뉴를 등록할 수 있습니다.");
        }

        // 가게 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

        if (!store.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인 가게에만 메뉴를 등록할 수 있습니다.");
        }

        Menu menu = new Menu(menuName, price, store, MenuStatus.OPEN);
        Menu savedMenu = menuRepository.save(menu);

        return new MenuResponseDto(savedMenu.getId(), store.getId(), savedMenu.getMenuName(), savedMenu.getPrice());

    }

    // 메뉴 수정
    @Transactional
    public void updateMenu(Long menuId, String email, String menuName, int price) {

        // 사용자 확인
        User user = userRepository.findUserByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        if (user.getRole() != UserRole.OWNER) {
            throw new IllegalArgumentException("사장님만 메뉴를 수정할 수 있습니다.");
        }

        // 메뉴 확인
        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->
                new IllegalArgumentException("해당 메뉴가 존재하지 않습니다."));

        if (!menu.getStore().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인 가게의 메뉴만 수정할 수 있습니다.");
        }

        menu.updateMenu(menuName, price);
        menuRepository.save(menu);
    }

    public void deleteMenu(Long menuId, String email) {
        // 사용자 확인
        User user = userRepository.findUserByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        if (user.getRole() != UserRole.OWNER) {
            throw new IllegalArgumentException("사장님만 메뉴를 삭제할 수 있습니다.");
        }

        // 메뉴 삭제
        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->
                new IllegalArgumentException("해당 메뉴가 존재하지 않습니다."));

        if (!menu.getStore().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인의 가게의 메뉴만 삭제할 수 있습니다.");
        }

        // 상태 변경
        menuRepository.updateStatus(menuId, MenuStatus.CLOSE);
    }
}
