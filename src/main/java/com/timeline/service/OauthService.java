package com.timeline.service;

import com.timeline.controller.dto.MemberOauthAttributeDto;
import com.timeline.entity.Member;
import com.timeline.oauth.SocialLoginType;
import com.timeline.oauth.social.SocialOauth;
import com.timeline.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 2:25
 * @brief : 구글/네이버/카카오 등 소셜 서비스 처리
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthService {
    private final List<SocialOauth> socialOauthList;
    private final HttpServletResponse response;
    private final MemberRepository memberRepository;

    public void request(SocialLoginType socialLoginType) {
        log.info("[ OauthService - request ]");
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL(); // getOauthRedirectURL : redirect 처리를 할 URL생성
        try {
            response.sendRedirect(redirectURL);
            log.info("---redirectURL--->" + redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        * 설명 : 이게 어떤 과정인가하면 내 서비스가 예를들어 쇼핑몰 서비스라고 가정했을때 사용자가 구글 로그인 요청을 했을때 구글 웹페이지에서 사용자의 브라우저 세션에 구글 계정이 로그인되어있는가 및 구글 로그인을 할 수 있는 페이지로 이동되어야 됩니다.
        * OauthService에서 각 소셜 서비스 타입별로 분기처리하여 getOauthRedirectURL 메소드를 호출할 수 있도록 하고 알 수 없는 타입의 파라미터가 전달되었을때는 Exception을 발생시키도록 처리했습니다
        * */
    }

    public String requestAccessToken(SocialLoginType socialLoginType, String code) {
        log.info("[ OauthService - requestAccessToken ]");

        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        log.info("--- GoogleLogin ---> "+ socialOauth);

        return socialOauth.requestAccessToken(code);
    }


    /**
    * SocialLoginType에 맞는 SocialOauth 객체를 반환
     * 구글 -> GoogleOauth 반환
    * */
    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }


}