package com.toyprj.toyman.web;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyprj.toyman.domain.posts.Posts;
import com.toyprj.toyman.domain.posts.PostsRepository;
import com.toyprj.toyman.web.dto.PostsResponseDto;
import com.toyprj.toyman.web.dto.PostsSaveRequestDto;
import com.toyprj.toyman.web.dto.PostsUpdateRequestDto;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @SpringBootTest??? ????????? ??? JUnit??? @WebMvcTest, @WebFluxTest?????? ?????? ????????? ????????? ?????? ????????? ?????? ?????????
// @SpringBootTest.WebEnvironment.RANDOM_PORT: ????????? ??????????????? ????????? ??????, ?????? ????????? ????????? ???????????? ?????????

class PostApiControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @LocalServerPort // @LocalServerPort: ?????? ????????? ????????? ???????????? ???????????? ??? ??? ??????
    private int port;

    @Autowired // @Autowired: ???????????? ???????????? ???(Bean)??? ?????? ??????
    //TestRestTemplate: ??????????????? ???????????? ???????????? HTTP ???????????????
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;


    // @Before: JUnit?????? ?????? ???????????? ???????????? ?????? ???????????? ???????????? ??????
    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                             .apply(springSecurity())
                             .build();
    }

    @After
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("posts ??????")
    void postsSaves() throws Exception {
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
        // postForEntity: HTTP POST ????????? ?????????, ???????????? ResponseEntity??? ??????
        // ResponseEntity: HTTP ????????? ???????????? ?????????
        // HttpStatus.OK: 200 OK
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
//        // then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        //???etBody(): ?????? ????????? ??????
//        //isGreaterThan(): ?????? ????????? 0?????? ?????? ??????
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//      // when
        mvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(new ObjectMapper().writeValueAsString(requestDto)))
           .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        //findAll(): ????????? posts??? ?????? ?????? ???????????? ??????????????? ?????????
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("POST????????????")
    void postInModify() throws Exception {
        // given
        Posts savePosts = postsRepository.save(Posts.builder()
                                                    .title("title")
                                                    .content("content")
                                                    .author("author")
                                                    .build());

        Long UpdateId = savePosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                                                                .title(expectedTitle)
                                                                .content(expectedContent)
                                                                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + UpdateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);


        mvc.perform(put(url)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(new ObjectMapper().writeValueAsString(requestDto)))
           .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);


        // when
//        ResponseEntity<Long> responseEntity = restTemplate.
//            //exchange: HTTP PUT ????????? ?????????, ???????????? ResponseEntity??? ??????
//                exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        // then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        //getBody(): ?????? ????????? ??????
//        //isGreaterThan(): ?????? ????????? 0?????? ?????? ??????
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
//        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }


}