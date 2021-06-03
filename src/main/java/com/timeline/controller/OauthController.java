package com.timeline.controller;

import com.timeline.oauth.SocialLoginType;
import com.timeline.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 3:32
 * @brief : 소셜 서비스 로그인/ 회원가입 처리
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    /* 소셜 서비스 로그인/ 회원가입 */

    /**
     * 1.
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     * @param socialLoginType (GOOGLE, FACEBOOK, NAVER, KAKAO)
     */
    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);
    }

    /**
     * 2.
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param socialLoginType (GOOGLE, FACEBOOK, NAVER, KAKAO)
     * @param code API Server 로부터 넘어노는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 String 문자열 (access_token, refresh_token 등)
     */
    @GetMapping(value = "/code/{socialLoginType}")
    public String callback(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        return oauthService.requestAccessToken(socialLoginType, code);
    }


}
