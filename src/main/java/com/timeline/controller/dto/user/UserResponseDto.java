package com.timeline.controller.dto.user;

import com.timeline.domain.user.Role;
import com.timeline.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private String email;
    private String number;
    private String nickName;
    private String password;
    private String category;
    private String crtClass;
    private Role role;

    public UserResponseDto(User entity){
        this.email = entity.getEmail();
        this.number = entity.getNumber();
        this.nickName = entity.getNickName();
        this.password = entity.getPassword();
        this.category = entity.getCategory();
        this.crtClass = entity.getCrtClass();
        this.role = entity.getRole();

    }


}
