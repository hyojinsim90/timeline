package com.timeline.oauth.social;

import com.timeline.oauth.SocialLoginType;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 2:18
 * @brief : 소셜 로그인 타입별로 공통적으로 사용될 interface 생성
 **/
public interface SocialOauth {

    /**
     * 각 Social Login 페이지로 Redirect 처리할 URL Build
     * 사용자로부터 로그인 요청을 받아 Social Login Server 인증용 code 요청
     * 인터페이스므로 각 소셜 서비스의 socialOauth로 감
     * Google이면 -> GoodleOauth로
     */
    String getOauthRedirectURL();

    /**
     * API Server로부터 받은 code를 활용하여 사용자 인증 정보 요청
     * @param code API Server 에서 받아온 code
     * @return API 서버로 부터 응답받은 Json 형태의 결과를 string으로 반
     */
    String requestAccessToken(String code);

    default SocialLoginType type() {
        if (this instanceof FacebookOauth) {
            return SocialLoginType.FACEBOOK;
        } else if (this instanceof GoogleOauth) {
            return SocialLoginType.GOOGLE;
        } else if (this instanceof NaverOauth) {
            return SocialLoginType.NAVER;
        } else if (this instanceof KakaoOauth) {
            return SocialLoginType.KAKAO;
        } else {
            return null;
        }
    }

}
