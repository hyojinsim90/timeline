package com.timeline.controller.dto.member;

import com.timeline.entity.member.Member;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-22 오전 4:59
 * @brief :
 **/
public class MemberIdResponseDto {
    private Long id;


    public MemberIdResponseDto(Member entity) {
        this.id = entity.getId();
    }
}
