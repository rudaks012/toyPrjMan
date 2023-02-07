package com.toyprj.toyman.domain.posts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity // 테이블과 링크될 클래스임을 나타냄 (기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭)
public class Posts {

    @Id // 해당 테이블의 PK 필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙을 나타냄 (기본값은 AUTO, MySQL의 auto_increment와 같이 자동증가하는 정수형 값이 됨)
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 칼럼을 나타냄 (기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
