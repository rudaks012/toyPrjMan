package com.toyprj.toyman.config.auth;

import com.toyprj.toyman.config.auth.dto.OauthAttributes;
import com.toyprj.toyman.config.auth.dto.SessionUser;
import com.toyprj.toyman.domain.user.User;
import com.toyprj.toyman.domain.user.UserRepository;
import java.util.Collections;
import javax.persistence.Entity;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 지금은 구글만 사용하는 불필요한 값이지만, 이후 네이버 로그인 연동시에 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration()
                                           .getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드값. PK와 같은 의미 // OAuth2 로그인 진행 시 키가 되는 필드값. PK와 같은 의미
        // 구글의 경우 기본적으로 코드 지원 -> "sub"
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                                                  .getUserInfoEndpoint()
                                                  .getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OauthAttributes attributes = OauthAttributes.of(registrationId, userNameAttributeName,oAuth2User.getAttributes());

        User user = SaveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user)); // 세션에 사용자 정보를 저장하기 위한 Dto 클래스

        return new DefaultOAuth2User(
            Collections.singleton(
                (new SimpleGrantedAuthority(user.getRoleKey()))),
            attributes.getAttributes(),
            attributes.getNameAttributeKey());
    }

    // 구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능도 같이 구현
    private User SaveOrUpdate(OauthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                                  .map(entity -> entity.update(attributes.getName(),attributes.getPicture()))
                                  .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
