package com.toyprj.toyman.web.dto;

import com.toyprj.toyman.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 기본 생성자 자동 추가
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성
    // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함

    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                    .title(title)
                    .content(content)
                    .author(author)
                    .build();
    }
}
