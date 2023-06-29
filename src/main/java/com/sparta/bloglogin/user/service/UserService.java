package com.sparta.bloglogin.user.service;


import com.sparta.bloglogin.common.code.HanghaeBlogErrorCode;
import com.sparta.bloglogin.common.exception.HanghaeBlogException;
import com.sparta.bloglogin.common.jwt.JwtUtil;
import com.sparta.bloglogin.user.dto.LoginRequestDto;
import com.sparta.bloglogin.user.dto.SignupRequestDto;
import com.sparta.bloglogin.user.entity.User;
import com.sparta.bloglogin.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String role = requestDto.getRole();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new HanghaeBlogException(HanghaeBlogErrorCode.IN_USED_USERNAME, null);
        }

        User userEntity = new User(username, password, role);
        userRepository.save(userEntity);
    }


    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_USER, null)
        );

        if (!user.getPassword().equals(password)) {
            throw new HanghaeBlogException(HanghaeBlogErrorCode.WRONG_PASSWORD, null);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
    }
}
