package com.timeline.domain.user;

import com.timeline.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private String email;

    private String number;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Column
    private String category;

    @Column
    private String crtClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String email, String number, String nickName, String password, String category, String crtClass, Role role) {
        this.email = email;
        this.number = number;
        this.nickName = nickName;
        this.password = password;
        this.category = category;
        this.crtClass = crtClass;
        this.role = role;
    }


    public String getRoleKey() {
        return this.role.getKey();
    }

    public void update(String number, String nickName, String password, String category) {
        this.number = number;
        this.nickName = nickName;
        this.password = password;
        this.category = category;
    }

}
