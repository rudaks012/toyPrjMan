package com.toyprj.toyman.domain.posts;

import com.toyprj.toyman.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 기본 생성자 자동 추가 (public Posts() {}와 같은 효과)
@Entity // 테이블과 링크될 클래스임을 나타냄 (기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭)
public class Posts extends BaseTimeEntity {

    @Id // 해당 테이블의 PK 필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙을 나타냄 (기본값은 AUTO, MySQL의 auto_increment와 같이 자동증가하는 정수형 값이 됨)
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 칼럼을 나타냄 (기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용)
    private String title;

    //columnDefinition을 사용하면 테이블의 칼럼을 선언할 때 타입을 지정할 수 있음
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성 (생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함)
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
