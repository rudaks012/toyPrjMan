package com.toyprj.toyman.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//target은 어노테이션이 생성될 수 있는 위치를 지정
//paramter는 메소드의 파라미터로 선언된 객체에서만 사용할 수 있음
//retention은 어노테이션의 범위를 지정
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {

}
