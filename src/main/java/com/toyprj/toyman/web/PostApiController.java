package com.toyprj.toyman.web;

import com.toyprj.toyman.service.posts.PostsService;
import com.toyprj.toyman.web.dto.PostsSaveRequestDto;
import com.toyprj.toyman.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성
@RestController // JSON을 반환하는 컨트롤러로 만들어 줌
public class PostApiController {

    private final PostsService postService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    //PutMapping은 전체를 수정하는 것이고 PatchMapping은 일부를 수정하는 것
    //api같은경우 url에만 봐도 API를 알 수있다.

    //왜 PutMapping을 사용했을까?
    //PutMapping은 전체를 수정하는 것이고 PatchMapping은 일부를 수정하는 것
    //api같은경우 url에만 봐도 API를 알 수있다.
    //그래서 PutMapping을 사용했다.
    //localhost:8080/api/v1/posts/1 이런식으로 url을 보면
    //posts/1 이라는 것을 알 수 있다
    //putmapping 이라서 안됨 -> 이거 form에서 put으로 보내면 된다
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }


}
