package com.example.samsung_delivery.controller;


import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.LoginResponseDto;
import com.example.samsung_delivery.dto.store.StoreRequestDto;
import com.example.samsung_delivery.dto.store.StoreResponseDto;
import com.example.samsung_delivery.enums.UserRole;
import com.example.samsung_delivery.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    //가게 create
    @PostMapping
    public ResponseEntity<StoreResponseDto> crateStore(@RequestBody StoreRequestDto storeRequestDto,
                                                       HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if(session == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자 입니다.");
        }
        LoginResponseDto dto = (LoginResponseDto)session.getAttribute(Const.LOGIN_USER);
        String email = dto.getEmail();

        StoreResponseDto store = storeService.createStore(storeRequestDto, email);
        return new ResponseEntity<>(store, HttpStatus.OK);
    }

    //단건 read

    //다건 read

    //정보 수정 update

    //폐업 update
}
