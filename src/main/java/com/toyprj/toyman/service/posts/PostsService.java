package com.toyprj.toyman.service.posts;

import com.toyprj.toyman.domain.posts.PostsRepository;
import com.toyprj.toyman.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성
// @Service // 서비스 클래스에는 @Service 어노테이션을 사용
// @Transactional // 트랜잭션, 서비스 클래스에는 @Transactional 어노테이션을 사용
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

}
