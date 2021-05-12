package com.timeline.oauth.social;

import org.springframework.stereotype.Component;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 2:21
 * @brief : 공통 interface(SocialOauth)를 구현할 소셜 로그인 네이버 타입 Class 생성
 **/

@Component
public class NaverOauth implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }

    @Override
    public String requestAccessToken(String code) {
        return null;
    }
}