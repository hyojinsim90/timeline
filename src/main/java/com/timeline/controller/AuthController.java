package com.timeline.controller;


import com.timeline.controller.dto.*;
import com.timeline.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 4:41
 * @brief : 일반 로그인 / 회원가입
**/
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /* 일반 로그인/ 회원가입 */

    @GetMapping("/hello")
    public String hello() {
        return "aws test success!";
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberSaveRequestDto memberSaveRequestDto) {
        return ResponseEntity.ok(authService.signup(memberSaveRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(HttpServletResponse response, @RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(response, memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(HttpServletResponse response, @RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(response, tokenRequestDto));
    }



}
