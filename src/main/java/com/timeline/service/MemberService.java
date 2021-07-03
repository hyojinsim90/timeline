package com.timeline.service;

import com.timeline.controller.dto.member.MemberListResponseDto;
import com.timeline.controller.dto.member.MemberResponseDto;
import com.timeline.controller.dto.member.MemberUpdateRequestDto;
import com.timeline.entity.Member;
import com.timeline.entity.RefreshToken;
import com.timeline.jwt.JwtFilter;
import com.timeline.jwt.TokenProvider;
import com.timeline.repository.MemberRepository;
import com.timeline.repository.RefreshTokenRepository;
import com.timeline.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 5:00
 * @brief : 인증된 유저 CRUD 처리
**/
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;


    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo() {
        log.info("[MemberService - getMyInfo]");
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    /* 유저 정보 가져옴(한명) */
    @Transactional(readOnly = true)
    public MemberListResponseDto getMemberInfo(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberListResponseDto::new)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    /* 전체 유저 정보 가져옴 */
    @Transactional(readOnly = true) // readOnly : 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도 개선
    public List<MemberListResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberListResponseDto::new) // = .map(Member -> new MemberListResponseDto(Member))
                .collect(Collectors.toList());
        // MemberListResponseDto 넘어온 Member Stream을 map을 통해 MemberListResponseDto 변환 -> List로 반환
    }

    /* 회원정보 수정 */
    @Transactional
    public MemberListResponseDto update(String email, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다. email =" + email));

        log.info("getGubun() - origin ---> " + member.getGubun() );
        if("google".equals(member.getGubun())){
            requestDto.setPassword(passwordEncoder.encode("google"));
            log.info("setPassword() - google ---> " + requestDto.getPassword() );
        } else {
            requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            log.info("getPassword() - non-google ---> " + requestDto.getPassword() );
        }

        member.update(requestDto.getPassword(), requestDto.getNickname());

        log.info("---member ---> " + member );

        return new MemberListResponseDto(member);
    }

    /* 회원정보 삭제 */
    @Transactional
    public void delete(String email){

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다. email =" + email));

        // refresh token에서 먼저 삭제
        RefreshToken refreshToken = refreshTokenRepository.findByKey(member.getId().toString());
        refreshTokenRepository.delete(refreshToken);

        // 존재하는 Member인지 확인을 위해 엔티티 조회 후 삭제
        memberRepository.delete(member);

    }




}
