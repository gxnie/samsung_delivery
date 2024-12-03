package com.example.samsung_delivery.service;

import com.example.samsung_delivery.dto.StoreRequestDto;
import com.example.samsung_delivery.dto.StoreResponseDto;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.repository.StoreRepository;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일과 관련된 사용자를 찾을 수 없음"));

        Store store = new Store(findUser, storeRequestDto.getStoreName(), storeRequestDto.getOpenTime(), )
    }
}
