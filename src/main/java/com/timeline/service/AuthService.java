package com.timeline.service;

import com.timeline.controller.dto.FindPwMailDto;
import com.timeline.controller.dto.auth.TokenDto;
import com.timeline.controller.dto.auth.TokenRequestDto;
import com.timeline.controller.dto.member.*;
import com.timeline.entity.Member;
import com.timeline.entity.RefreshToken;
import com.timeline.jwt.TokenProvider;
import com.timeline.repository.MemberRepository;
import com.timeline.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional
    public MemberResponseDto signup(MemberSaveRequestDto memberSaveRequestDto) {
        log.info("[signup]");

        if (memberRepository.existsByEmail(memberSaveRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = memberSaveRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public TokenDto login(HttpServletResponse response, MemberRequestDto memberRequestDto) {
        log.info("[login]");

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
        log.info("-- UsernamePasswordAuthenticationToken 생성 " );
        log.info("--- name 확인 : " + authenticationToken.getName() ); // 이메일
        log.info("--- principal(이메일)확인 : " + authenticationToken.getPrincipal() ); //이메일
        log.info("--- Credentials(비밀번호) 확인 :  " + authenticationToken.getCredentials() ); // 비밀번호


        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        log.info("--- AuthenticationManagerBuilder : " + authenticationManagerBuilder.getObject()); // ProviderManager 클래스
        log.info("--- 유저정보 : " + authentication.getPrincipal()); // userdetails(아이디,비번(보호됨)권한)
        log.info("--- 권한 : " + authentication.getAuthorities()); // 권한 ex) ROLE_USER

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(response,authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        log.info("--- refreshToken : " + refreshToken );
        log.info("--- authentication 아이디 : " + authentication.getName() ); // 1 (순서)

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(HttpServletResponse response, TokenRequestDto tokenRequestDto) {

        log.info("[reissue]");

        // 1. Refresh Token 검증
        log.info("- 1. Refresh Token 검증 : " + tokenProvider.validateToken(tokenRequestDto.getRefreshToken()));
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            log.info("- Refrech Token : " + tokenRequestDto.getRefreshToken());
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName());
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

    /* 로그아웃 */
    @Transactional
    public ResponseEntity<String> logout(String email) {
        log.info("[로그아웃]");

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다. email =" + email));

        if("general".equals(member.getGubun())){
            // refresh token에서 먼저 삭제
            RefreshToken refreshToken = refreshTokenRepository.findByKey(member.getId().toString());

            if(refreshToken != null){
                refreshTokenRepository.delete(refreshToken);
            }

            return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully ! ");
        }
        return null;
    }

    /* 이메일 존재 체크 */
    public boolean checkMail(String email) {
        return memberRepository.existsByEmail(email);
    }

    /* 비밀번호 찾기 - 인증 이메일 발송 */
    @Transactional
    public void findPw(MemberEmailRequestDto memberEmailRequestDto) {
        log.info("[send email to find password]");

        String email = memberEmailRequestDto.getEmail();

        // 임시 비밀번호 생성
        String str = getTempPassword();

        // 인증 이메일 만들기
        FindPwMailDto findPwMailDto = FindPwMailDto.builder()
                            .address(email)
                            .title("TIMELINE 임시비밀번호 안내 이메일 입니다.")
                            .message("안녕하세요. TIMELINE 임시비밀번호 안내 관련 이메일 입니다. 임시 비밀번호는 " + str + " 입니다")
                            .build();

        // 임시 비밀번호 유저 정보에 저장
        updatePassword(str,email);

        // 메일 발송
        mailSend(findPwMailDto);

    }

    /* 비밀번호 찾기 - 3. 인증 이메일 발송 */
    private void mailSend(FindPwMailDto findPwMailDto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(findPwMailDto.getAddress()); // 받는사람 주소
        message.setFrom("timeline0418@gmail.com"); // 보내는사람 주소
        message.setSubject(findPwMailDto.getTitle()); // 메일 제목
        message.setText(findPwMailDto.getMessage()); // 메일 내용

        log.info("- Check mail message : " + message);

        javaMailSender.send(message); // 메일 발송

        log.info("이메일 전송 완료!");

    }

    /* 비밀번호 찾기 - 2. 임시비밀번호 멤버정보에 저장 */
    private void updatePassword(String str, String email) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다. email =" + email));
        member.updatePw(passwordEncoder.encode(str));

//        return new MemberListResponseDto(member);
    }

    /* 비밀번호 찾기 - 1. 임시비밀번호 생성 */
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }


    /* 전체 닉네임 가져옴 */
    @Transactional(readOnly = true)
    public List<MemberNicknameResponseDto> findAllNickname() {
        log.info("[service- findAllNickname]");
        return memberRepository.findAll().stream()
                .map(MemberNicknameResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 한명 닉네임 가져옴 */
    @Transactional(readOnly = true)
    public MemberNicknameResponseDto findNickname(String email) {
        log.info("[service- findNickname]");

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다. email =" + email));

        return new MemberNicknameResponseDto(member);
    }

    /* 한명 멤버 아이디 가져옴  */
    public Long getId(String email) {
        log.info("[service- getId]");

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다. email =" + email));

        return member.getId();
    }
}
