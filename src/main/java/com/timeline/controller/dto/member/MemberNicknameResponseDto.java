package com.timeline.controller.dto.member;

import com.timeline.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-31 오후 7:13
 * @brief : 닉네임 리스트 리턴용 dto
 **/

@Getter
public class MemberNicknameResponseDto {
    private String nickname;


    public MemberNicknameResponseDto(Member entity) {
        this.nickname = entity.getNickname();
    }
}

