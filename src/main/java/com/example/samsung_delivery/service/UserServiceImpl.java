package com.example.samsung_delivery.service;

import com.example.samsung_delivery.config.PasswordEncoder;
import com.example.samsung_delivery.config.SignUpValidation;
import com.example.samsung_delivery.dto.login.LoginResponseDto;
import com.example.samsung_delivery.dto.user.UserResponseDto;
import com.example.samsung_delivery.entity.User;
import com.example.samsung_delivery.enums.UserRole;
import com.example.samsung_delivery.enums.UserStatus;
import com.example.samsung_delivery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignUpValidation signUpValidation;

    // 회원 가입
    @Transactional
    @Override
    public UserResponseDto signUp(String email, String password, UserRole role){

        // 이메일과 비밀번호 형식이 맞지 않는 경우
        if (!signUpValidation.isValidEmail(email)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일 형식이 다릅니다.");
        }

        if (!signUpValidation.isValidPassword(password)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 형식이 다릅니다.");
        }

        // 같은 아이디 존재할 시 가입 불가
        if (userRepository.findUserByEmail(email).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "같은 아이디가 존재합니다.");
        }

        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.getStatus() == UserStatus.DEACTIVATED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제된 이메일 입니다.");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "같은 아이디가 존재합니다.");
        }
        User user = new User(email,passwordEncoder.encode(password),role);
        User saveUser = userRepository.save(user);

        return new UserResponseDto(saveUser.getId(),saveUser.getEmail(),saveUser.getRole());
    }

    // 회원 탈퇴
    @Transactional
    @Override
    public void deactivateUser(Long userId, String password) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.setStatus(UserStatus.DEACTIVATED);
        userRepository.save(user);
    }


    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
    }

    // 사용자 로그인, 이메일과 비밀번호를 통해 사용자 인증
    @Override
    public LoginResponseDto login(String email, String password) {

        // 이메일을 기반으로 사용자 찾기
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀 번호가 일치하지 않습니다.");
        }

        // 인증된 사용자 정보 반환
        return new LoginResponseDto(user.getId(),user.getEmail(), user.getRole());
    }
}
