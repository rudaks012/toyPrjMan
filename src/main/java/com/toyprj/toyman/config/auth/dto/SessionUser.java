package com.toyprj.toyman.config.auth.dto;

import com.toyprj.toyman.domain.user.User;
import java.io.Serializable;
import lombok.Getter;

//serialize는 객체를 직렬화하는 것을 의미
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
