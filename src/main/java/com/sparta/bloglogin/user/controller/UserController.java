package com.sparta.bloglogin.user.controller;

import com.sparta.bloglogin.common.dto.ApiResult;
import com.sparta.bloglogin.user.dto.LoginRequestDto;
import com.sparta.bloglogin.user.dto.SignupRequestDto;
import com.sparta.bloglogin.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public ApiResult signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return new ApiResult("회원가입 성공", HttpStatus.OK.value());
    }

    @PostMapping("/login")
    public ApiResult login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        userService.login(requestDto, response);
        return new ApiResult("로그인 성공", HttpStatus.OK.value());
    }
}
