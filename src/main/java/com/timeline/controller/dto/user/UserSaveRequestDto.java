package com.timeline.controller.dto.user;

import com.timeline.domain.user.Role;
import com.timeline.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;
    private String number;
    private String nickName;
    private String password;
    private String category;
    private String crtClass;
    private Role role;

    public User toEntity() {
        return User.builder()
                .email(email)
                .number(number)
                .nickName(nickName)
                .password(password)
                .category(category)
                .crtClass(crtClass)
                .role(role)
                .build();
    }
}
