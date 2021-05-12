package com.timeline.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 2:15
 * @brief :
 **/

/*
* Controller에서 socialLoginType 파라미터(@PathVariable을 통해)를 받는데
* enum 타입의 대문자 값을 소문자로 mapping 가능하도록 하기위하여 생성
* */
@Configuration
public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {
    @Override
    public SocialLoginType convert(String s) {
        return SocialLoginType.valueOf(s.toUpperCase());
    }
}