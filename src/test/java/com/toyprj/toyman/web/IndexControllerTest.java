package com.toyprj.toyman.web;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment =  RANDOM_PORT)
class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void mainPageLoading() {
        // when
        // "/" 주소로 HTTP GET 요청을 하면, index.mustache 파일이 반환된다.
        // 이때, index.mustache 파일은 src/main/resources/templates/index.mustache 에 위치한다.
        String body = this.restTemplate.getForObject("/", String.class);
        // then
        assertThat(body).contains("스프링부트로 시작하는 웹 서비스");
    }
}