package com.example.samsung_delivery.controller;


import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.store.AllStoreResponseDto;
import com.example.samsung_delivery.dto.store.StoreRequestDto;
import com.example.samsung_delivery.dto.store.StoreResponseDto;
import com.example.samsung_delivery.entity.Store;
import com.example.samsung_delivery.enums.StoreStatus;
import com.example.samsung_delivery.repository.StoreRepository;
import com.example.samsung_delivery.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;
    private final StoreRepository storeRepository;

    public StoreController(StoreService storeService, StoreRepository storeRepository) {
        this.storeService = storeService;
        this.storeRepository = storeRepository;
    }


    //가게 create
    @PostMapping
    public ResponseEntity<StoreResponseDto> crateStore(@RequestBody StoreRequestDto storeRequestDto,
                                                       HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        LoginResponseDto dto = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);
        String email = dto.getEmail();
        Long userId = dto.getUserId();

        String role = String.valueOf(dto.getUserRole());
        roleValidation(role);

        Long storeCount = storeRepository.countByUserIdAndStatus(userId, StoreStatus.ACTIVE);

        if (storeCount >= 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 가게가 3개 초과 입니다");
        }

        StoreResponseDto store = storeService.createStore(storeRequestDto, email);
        return new ResponseEntity<>(store, HttpStatus.OK);
    }

    //단건 read
    @GetMapping("/{id}")
    public ResponseEntity<StoreResponseDto> getStore(@PathVariable Long id,
                                                     HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        Optional<Store> existStore = storeRepository.findById(id);

        if (existStore.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가게가 없습니다.");
        }

        StoreResponseDto store = storeService.findByStoreId(id);

        return new ResponseEntity<>(store, HttpStatus.OK);


    }

    //다건 read
    @GetMapping
    public ResponseEntity<List<AllStoreResponseDto>> getStores(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        List<AllStoreResponseDto> store = storeService.getAllStores();

        return new ResponseEntity<>(store, HttpStatus.OK);
    }

    //정보 수정 update
    @PatchMapping("/{id}")
    public ResponseEntity<StoreResponseDto> updateStore(@PathVariable Long id,
                                                        @RequestBody StoreRequestDto storeRequestDto,
                                                        HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        LoginResponseDto dto = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);
        Long userId = dto.getUserId();
        String role = String.valueOf(dto.getUserRole());
        roleValidation(role);

        StoreResponseDto updateDto = storeService.updateStore(id, storeRequestDto, userId);

        return new ResponseEntity<>(updateDto, HttpStatus.OK);


    }

    //폐업 update
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable Long id,
                                              HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        LoginResponseDto dto = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);
        Long userId = dto.getUserId();
        String role = String.valueOf(dto.getUserRole());
        roleValidation(role);

        storeService.closeStore(id, userId);

        return ResponseEntity.ok().body("폐업처리가 완료되었습니다.");

    }


    private void roleValidation(String role) {
        if (!role.equals("OWNER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사장님이 아닙니다.");
        }
    }


}
