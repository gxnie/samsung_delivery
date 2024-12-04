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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

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

        // !store.getStatus().equals(UserRole.OWNER)
        if (!store.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인 가게에만 메뉴를 등록할 수 있습니다.");
        }

        // 메뉴 생성 및 사장님, 가게 설정
        Menu menu = new Menu(menuName, price, store, MenuStatus.OPEN);
        Menu savedMenu = menuRepository.save(menu);

        return new MenuResponseDto(savedMenu.getId(), store.getId(), savedMenu.getMenuName(), savedMenu.getPrice());
    }

}