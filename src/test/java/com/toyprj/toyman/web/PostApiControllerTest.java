package com.toyprj.toyman.web;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.toyprj.toyman.domain.posts.Posts;
import com.toyprj.toyman.domain.posts.PostsRepository;
import com.toyprj.toyman.web.dto.PostsSaveRequestDto;
import java.util.List;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @SpringBootTest를 사용할 때 JUnit의 @WebMvcTest, @WebFluxTest와는 달리 스프링 부트의 기본 설정을 모두 로드함
// @SpringBootTest.WebEnvironment.RANDOM_PORT: 서블릿 컨테이너를 띄우지 않고, 실제 서버의 포트를 사용하는 테스트

class PostApiControllerTest {

    @LocalServerPort // @LocalServerPort: 실제 서버의 포트를 사용하는 테스트를 할 때 사용
    private int port;

    @Autowired // @Autowired: 스프링이 관리하는 빈(Bean)을 주입 받음
    //TestRestTemplate: 스프링에서 제공하는 테스트용 HTTP 클라이언트
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("posts 등록")
    void postsSaves() {
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                            .title(title)
                                                            .content(content)
                                                            .author("author")
                                                            .build();
        String url = "http://localhost:" + port + "/api/v1/posts";
        // when
        // postForEntity: HTTP POST 요청을 보내고, 응답으로 ResponseEntity를 받음
        // ResponseEntity: HTTP 응답을 나타내는 클래스
        // HttpStatus.OK: 200 OK
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto,
            Long.class);
        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //ㅎetBody(): 응답 본문을 받음
        //isGreaterThan(): 응답 본문이 0보다 큰지 확인
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        //findAll(): 테이블 posts에 있는 모든 데이터를 조회해오는 메소드
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }


}