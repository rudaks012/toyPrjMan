package com.toyprj.toyman.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // 선언된 모든 final 필드가 포함된 생성자를 생성해줌
public enum Role {
    // 스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야만 함
    // 그래서 코드별 키 값을 ROLE_GUEST, ROLE_USER로 지정
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}
