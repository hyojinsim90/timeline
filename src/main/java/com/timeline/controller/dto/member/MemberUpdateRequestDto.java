package com.timeline.controller.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 5:21
 * @brief : 유저 수정 Dto
 **/
@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateRequestDto {

    private String password;
    private String nickname;

    @Builder
    public MemberUpdateRequestDto(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }

}
