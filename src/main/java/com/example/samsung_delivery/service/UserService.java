package com.example.samsung_delivery.service;

import com.example.samsung_delivery.config.PasswordEncoder;
import com.example.samsung_delivery.dto.LoginRequestDto;
import com.example.samsung_delivery.dto.UserRequestDto;
import com.example.samsung_delivery.dto.UserResponseDto;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        // 이메일 중복검사
        findByEmail(userRequestDto.getEmail());
        User savedUser = userRepository.save(userRequestDto.toEntity());
        return UserResponseDto.todto(savedUser);
    }

    // 이메일 중복검사
    public void findByEmail(String email) {
        if(userRepository.findUserByEmail(email).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "중복된 이메일입니다.");
        }
    }

    // 회원탈퇴
    @Transactional
    public void deleteUser(Long id){
        findUserById(id);
        userRepository.deleteById(id);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }


}
