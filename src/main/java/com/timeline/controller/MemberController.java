package com.timeline.controller;

import com.timeline.controller.dto.member.MemberListResponseDto;
import com.timeline.controller.dto.member.MemberResponseDto;
import com.timeline.controller.dto.member.MemberUpdateRequestDto;
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
 * @date : 2021-05-09 오전 4:51
 * @brief : 로그인 인증 후 유저 정보로 할수 있는 것들
**/
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        log.info("[MemberController - getMyMemberInfo - member/me]");
        return ResponseEntity.ok(memberService.getMyInfo());
    }

    /* 유저 정보 가져옴(한명) */
    @GetMapping("/{email}")
    public ResponseEntity<MemberListResponseDto> getMemberInfo(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberInfo(email));
    }

    /* 전체 유저 정보 가져옴 */
    @GetMapping("/ids")
    public List<MemberListResponseDto> findAll() {
        return memberService.findAll();
    }

    /* 회원정보 수정 */
    @PutMapping("/{email}")
    public ResponseEntity<MemberListResponseDto> update(@PathVariable String email, @RequestBody MemberUpdateRequestDto requestDto) {
        return ResponseEntity.ok(memberService.update(email, requestDto));
    }

    /* 회원정보 삭제 */
    @DeleteMapping("/{email}")
    public void delete(@PathVariable String email) {
        memberService.delete(email);
    }





}