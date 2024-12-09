package com.example.samsung_delivery.service;

import com.example.samsung_delivery.dto.menu.MenuResponseDto;
import com.example.samsung_delivery.dto.store.AllStoreResponseDto;
import com.example.samsung_delivery.dto.store.StoreRequestDto;
import com.example.samsung_delivery.dto.store.StoreResponseDto;
import com.example.samsung_delivery.entity.Menu;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.StoreStatus;
import com.example.samsung_delivery.repository.MenuRepository;
import com.example.samsung_delivery.repository.StoreRepository;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    //가게 생성
    @Transactional
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일과 관련된 사용자를 찾을 수 없음"));

        Store store = new Store(findUser, storeRequestDto.getCategory(), storeRequestDto.getStoreName(), storeRequestDto.getOpenTime(),storeRequestDto.getCloseTime(), storeRequestDto.getMinOrderPrice());

        Store savedStore = storeRepository.save(store);

        return new StoreResponseDto(savedStore);
    }

    //가게 단건 조회
    @Transactional(readOnly = true)
    public StoreResponseDto findByStoreId(Long id) {

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("가게가 없습니다 : " + id));

        if(store.getStatus().equals(StoreStatus.ACTIVE)) {
            List<Menu> menus = menuRepository.findByStoreId(id);
            List<MenuResponseDto> menuResponseDtos = menus.stream()
                    .map(MenuResponseDto::new)
                    .toList();
            return new StoreResponseDto(store, menuResponseDtos);
        }

        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "폐업된 가게 입니다.");
        }



    }
    
    //가게 전체 조회
    @Transactional
    public List<AllStoreResponseDto> getAllStores() {
        List<Store> stores = storeRepository.findByStatus(StoreStatus.ACTIVE);
        List<AllStoreResponseDto> storeResponseDtos = new ArrayList<>();
        for (Store store : stores) {
            storeResponseDtos.add(new AllStoreResponseDto(store));
        }
        return storeResponseDtos;
    }

    //가게 수정
    @Transactional
    public StoreResponseDto updateStore(Long storeId, StoreRequestDto storeRequestDto, Long userId) {

        Store store = findStoreById(storeId);

        if(!store.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "가게 주인이 아닙니다.");
        }
       store.update(storeRequestDto.getStoreName(), storeRequestDto.getOpenTime(),storeRequestDto.getCloseTime(),storeRequestDto.getMinOrderPrice());

        return new StoreResponseDto(storeRepository.save(store));
    }

    private Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(()-> new IllegalArgumentException("잘못된 가게 id 입니다."));
    }

    //가게 폐업
    @Transactional
    public void closeStore(Long storeId, Long userId) {
        Store store = findStoreById(storeId);
        if (store.getStatus() == StoreStatus.DEACTIVATED) {
            throw new IllegalArgumentException("이미 폐업된 가계 입니다.");
        }

        if(!store.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "가게 주인이 아닙니다.");
        }


        storeRepository.updateStatus(storeId, StoreStatus.DEACTIVATED);


    }
}
