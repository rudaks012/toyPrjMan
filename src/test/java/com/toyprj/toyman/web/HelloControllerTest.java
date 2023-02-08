package com.toyprj.toyman.web;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.regex.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

// @ExtendWith: JUnit5에서 JUnit4의 RunWith와 같은 기능
// JUnit5에서는 @RunWith 대신 @ExtendWith를 사용
// @RunWith(SpringRunner.class) 대신

@ExtendWith(SpringExtension.class)
// @WebMvcTest: @Controller, @ControllerAdvice 등을 사용할 수 있음
// @SpringBootTest: @Service, @Component, @Repository 등을 사용할 수 있음

@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc; // MockMvc: 웹 API를 테스트할 때 사용

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "Hello, World!";

        mvc.perform(
               get("/hello")) // MockMvc를 통해 /hello 주소로 HTTP GET 요청 (MockMvcRequestBuilders.get)
           .andExpect(status().isOk()) // mvc.perform의 결과를 검증 (MockMvcResultMatchers.status)
           // HTTP Header의 Status를 검증 (200, 404, 500 등)
           .andExpect(
               content().string(hello)); // mvc.perform의 결과를 검증 (MockMvcResultMatchers.content)
        // 응답 본문의 내용을 검증 (Controller에서 "Hello, World!"를 리턴하기 때문에 이 값이 맞는지 검증)
    }


    @Test
    @DisplayName("HelloDto가 리턴된다")
    void helloDtoReturn() throws Exception {
        // given
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
               .param("name", name) //API 테스트할때 사용될 요청 파라미터 설정 , 단 String만 허용됨 날짜,숫자 -> 문자열 변환 해야함
               .param("amount", String.valueOf(amount)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name", is(name))) //JSON응답 값을 필드별로 검증 -> $를 기준으로 필드명을 명시
           .andExpect(jsonPath("$.amount", is(amount)));

        // when

        // then
    }
}
