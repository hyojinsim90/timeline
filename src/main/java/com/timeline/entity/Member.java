package com.timeline.entity;

import lombok.*;

import javax.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(name = "gubun")
    private String gubun;

    @Builder
    public Member(String email, String password, String nickname, Authority authority, String gubun) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
        this.gubun = gubun;
    }

    public void update(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }

    public void updatePw(String password) {
        this.password = password;
    }


}
