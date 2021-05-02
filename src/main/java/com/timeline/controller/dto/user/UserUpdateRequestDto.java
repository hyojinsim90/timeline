package com.timeline.controller.dto.user;

import com.timeline.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String number;
    private String nickName;
    private String password;
    private String category;

    @Builder
    public UserUpdateRequestDto(String email, String number, String nickName, String password, String category, String crtClass, Role role) {
        this.number = number;
        this.nickName = nickName;
        this.password = password;
        this.category = category;
    }


}
