package com.timeline.controller.dto.member;

import com.timeline.entity.member.Authority;
import com.timeline.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-10 오전 1:09
 * @brief :
 **/

@Getter
@Setter
@NoArgsConstructor
public class MemberOauthAttributeDto {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickname;
    private String email;


    @Builder
    public MemberOauthAttributeDto(Map<String, Object> attributes, String nameAttributeKey, String nickname, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.email = email;
    }

    public static MemberOauthAttributeDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) { // of: OAuth2User에서 반환하는 사용자 정보는 Map이기떄문에 값 하나하나 변환해야함
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static MemberOauthAttributeDto ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return MemberOauthAttributeDto.builder()
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .authority(Authority.ROLE_USER)
                .build();
    }
    /*
     * toEntity
     * User 엔티티를 생성
     * OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때
     * role(Role.GUEST): 가입할 떄의 기본 권한을 GUEST로 줌
     * */


}
