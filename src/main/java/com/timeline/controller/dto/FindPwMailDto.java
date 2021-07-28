package com.timeline.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-21 오전 1:20
 * @brief : 비밀번호 찾기 - 이메일 인증
 **/
@Getter
@AllArgsConstructor
@Builder
public class FindPwMailDto {

    private String address;
    private String title;
    private String message;


}
