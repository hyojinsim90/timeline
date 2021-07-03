package com.timeline.controller.dto.member;

import com.timeline.entity.Authority;
import com.timeline.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 5:36
 * @brief : 전체 회원 조회 응답 Dto
 **/
@Getter
public class MemberListResponseDto {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Authority authority;
    private LocalDateTime modifiedDate;

    public MemberListResponseDto(Member entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.nickname = entity.getNickname();
        this.authority = entity.getAuthority();
        this.modifiedDate = entity.getModifiedDate();
    }
}
