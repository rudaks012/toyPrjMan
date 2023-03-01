package com.toyprj.toyman.service.posts;

import com.toyprj.toyman.domain.posts.Posts;
import com.toyprj.toyman.domain.posts.PostsRepository;
import com.toyprj.toyman.web.dto.PostsListResponseDto;
import com.toyprj.toyman.web.dto.PostsResponseDto;
import com.toyprj.toyman.web.dto.PostsSaveRequestDto;
import com.toyprj.toyman.web.dto.PostsUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// @Service // 서비스 클래스에는 @Service 어노테이션을 사용
// @Transactional // 트랜잭션, 서비스 클래스에는 @Transactional 어노테이션을 사용
@RequiredArgsConstructor// final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        // PostsResponseDto dto = new PostsResponseDto(entity);

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
            .map(PostsListResponseDto -> new PostsListResponseDto(PostsListResponseDto))
            .collect(Collectors.toList());
    }
}
