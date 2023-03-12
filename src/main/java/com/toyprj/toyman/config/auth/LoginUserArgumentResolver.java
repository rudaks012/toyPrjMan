package com.toyprj.toyman.config.auth;

import com.toyprj.toyman.config.auth.dto.SessionUser;
import java.lang.annotation.Annotation;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

//HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addArgumentResolvers()를 통해 추가해야 함
//HandlerMethodArgumentResolver는 조건에 맞는 경우 메소드가 있다면 HandlerMethodArgumentResolver의 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있음
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private  final HttpSession httpSession;
    @Override
    public boolean supportsParameter(MethodParameter parameter) { //컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
        boolean isLoginUserAnnotation = false;
        //매개변수에 특정 주석이 있는지 확인하고 @LoginUser 어노테이션이 붙어있는지 확인
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof LoginUser) {
                isLoginUserAnnotation = true;
                break;
            }
        }

        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        //파라미터 클래스 타입이 SessionUser.class인지 확인
        return isLoginUserAnnotation && isUserClass; //파라미터에 @LoginUser 어노테이션이 붙어있고, 파라미터 클래스 타입이 SessionUser.class인 경우 true
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return httpSession.getAttribute("user");
    }
}
