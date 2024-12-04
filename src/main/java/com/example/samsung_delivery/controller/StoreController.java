package com.example.samsung_delivery.controller;


import com.example.samsung_delivery.config.Const;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.store.StoreRequestDto;
import com.example.samsung_delivery.dto.store.StoreResponseDto;
import com.example.samsung_delivery.enums.StoreStatus;
import com.example.samsung_delivery.enums.UserRole;
import com.example.samsung_delivery.repository.StoreRepository;
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
        if(session == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자 입니다.");
        }
        LoginResponseDto dto = (LoginResponseDto)session.getAttribute(Const.LOGIN_USER);
        String email = dto.getEmail();
        Long userId = dto.getUserId();

        String role = String.valueOf(dto.getUserRole());
        roleValidation(role);

        Long storeCount = storeRepository.countByUserIdAndStatus(userId,StoreStatus.ACTIVE);

        if(storeCount >= 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 가게가 3개 초과 입니다");
        }

        StoreResponseDto store = storeService.createStore(storeRequestDto, email);
        return new ResponseEntity<>(store, HttpStatus.OK);
    }

    //단건 read

    //다건 read

    //정보 수정 update

    //폐업 update

    private void roleValidation(String role) {
        if(!role.equals("OWNER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사장님이 아닙니다.");
        }
    }


}
