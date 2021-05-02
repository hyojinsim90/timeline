package com.timeline.controller;

import com.timeline.controller.dto.user.UserResponseDto;
import com.timeline.controller.dto.user.UserSaveRequestDto;
import com.timeline.controller.dto.user.UserUpdateRequestDto;
import com.timeline.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 회원정보 하나 가져오기 - 내정보관리
    @GetMapping("user/{email}")
    public UserResponseDto findByEmail (@PathVariable String email) {
        return userService.findByEmail(email);
    }

    // 회원정보 전체 가져오기
    @GetMapping("user")
    public List findAllDesc () {
        return userService.findAll();
    }

    // 회원정보 수정
    @PutMapping("user/{email}")
    public String update(@PathVariable String email, @RequestBody UserUpdateRequestDto requestDto) {
        return userService.update(email, requestDto);
    }

    // 회원정보 삭제
    @PostMapping("user/{email}")
    public String save(@PathVariable String email) {
        userService.delete(email);
        return email;
    }

    // 회원정보 추가
    @PostMapping("user")
    public String save(@RequestBody UserSaveRequestDto requestDto) {
        return userService.save(requestDto);
    }

}
