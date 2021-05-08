package com.timeline.oauth.social;

import org.springframework.stereotype.Component;
/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 2:22
 * @brief : 공통 interface(SocialOauth)를 구현할 소셜 로그인 페이스북 타입 Class 생성
 **/

@Component
public class FacebookOauth implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }

    @Override
    public String requestAccessToken(String code) {
        return null;
    }
}