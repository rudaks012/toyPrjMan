package com.toyprj.toyman;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.toyprj.toyman.web.HelloController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

}
