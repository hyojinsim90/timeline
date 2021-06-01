package com.timeline.controller;


import com.timeline.controller.dto.auth.TokenDto;
import com.timeline.controller.dto.auth.TokenRequestDto;
import com.timeline.controller.dto.member.*;
import com.timeline.service.AuthService;
import com.timeline.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    private final MemberService memberService;

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

    @GetMapping("/logout/{email}")
    public ResponseEntity<String> logout(@PathVariable String email) {
        return authService.logout(email);
    }

    @GetMapping("/findPw/checkmail/{email}")
    public boolean checkMail (@PathVariable String email){
        log.info("[controller - /findPw/checkmail/{email}]");
        return authService.checkMail(email);
    }

    @PostMapping("/findPw/sendmail")
    public void findPw (@RequestBody MemberEmailRequestDto memberEmailRequestDto) {
        authService.findPw(memberEmailRequestDto);
    }

    /* 전체 닉네임 리스트 가져옴  */
    @GetMapping("/nicknames")
    public List<MemberNicknameResponseDto> findAll() {

        log.info("[controller - /auth/nicknames]");
        return authService.findAllNickname();
    }



}
