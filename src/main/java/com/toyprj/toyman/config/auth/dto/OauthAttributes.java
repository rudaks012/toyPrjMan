package com.toyprj.toyman.config.auth.dto;

import com.toyprj.toyman.domain.user.Role;
import com.toyprj.toyman.domain.user.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OauthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    //of()는 OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야만 함
    public static OauthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    //google 로그인 정보를 가져옴
    //of()에서 registrationId가 google인 경우 호출
    //of()에서 registrationId가 google이 아닌 경우에는 네이버 로그인 정보를 가져옴
    private static OauthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OauthAttributes.builder()
                              .name((String) attributes.get("name"))
                              .email((String) attributes.get("email"))
                              .picture((String) attributes.get("picture"))
                              .attributes(attributes)
                              .nameAttributeKey(userNameAttributeName)
                              .build();
    }

    public User toEntity() {
        return User.builder()
                   .name(name)
                   .email(email)
                   .picture(picture)
                   .role(Role.GUEST)
                   .build();


    }
}
