package com.timeline.service;

import com.timeline.controller.dto.auth.TokenDto;
import com.timeline.controller.dto.auth.TokenRequestDto;
import com.timeline.controller.dto.member.MemberRequestDto;
import com.timeline.controller.dto.member.MemberResponseDto;
import com.timeline.controller.dto.member.MemberSaveRequestDto;
import com.timeline.entity.Member;
import com.timeline.entity.RefreshToken;
import com.timeline.jwt.TokenProvider;
import com.timeline.repository.MemberRepository;
import com.timeline.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 2:25
 * @brief : 일반 로그인/회원가입의 서비스 - 인증토큰 발급 처리
**/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResponseDto signup(MemberSaveRequestDto memberSaveRequestDto) {
        if (memberRepository.existsByEmail(memberSaveRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = memberSaveRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public TokenDto login(HttpServletResponse response, MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(response,authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(HttpServletResponse response, TokenRequestDto tokenRequestDto) {

        log.info("[reissue]");

        // 1. Refresh Token 검증
        log.info("--- 1. Refresh Token 검증--->" + tokenProvider.validateToken(tokenRequestDto.getRefreshToken()));
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            log.info("---tokenRequestDto.getRefreshToken()--->" + tokenRequestDto.getRefreshToken());
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
        log.info("---authentication.getName()--->" + authentication.getName());
        log.info("---refreshToken--->" + refreshToken);

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            log.info("---refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())--->" + refreshToken.getValue().equals(tokenRequestDto.getRefreshToken()));
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(response, authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}
