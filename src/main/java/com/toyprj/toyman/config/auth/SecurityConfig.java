package com.toyprj.toyman.config.auth;

import com.toyprj.toyman.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화시켜 줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들을 disable함
            .and()
            .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점
            // 권한 관리 대상을 지정하는 옵션
            // URL, HTTP 메소드별로 관리가 가능
            // "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 줌
            // "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 함
            .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
            .antMatchers("/api/v1/**").hasRole(Role.USER.name())
            // 설정된 값들 이외 나머지 URL들을 나타냄
            .anyRequest().authenticated()
            // 로그아웃 기능에 대한 여러 설정의 진입점
            .and().logout().logoutSuccessUrl("/")
            // OAuth2 로그인 기능에 대한 여러 설정의 진입점
            .and().oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }

}
