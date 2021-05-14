package com.timeline.controller.dto.member;

import com.timeline.entity.Authority;
import com.timeline.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-09 오전 4:37
 * @brief : 유저 저장 요청 Dto
 **/
@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    private String email;
    private String password;
    private String nickname;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .authority(Authority.ROLE_USER)
                .build();
    }



}
