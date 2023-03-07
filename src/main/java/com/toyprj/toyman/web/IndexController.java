package com.toyprj.toyman.web;

import com.toyprj.toyman.config.auth.dto.SessionUser;
import com.toyprj.toyman.domain.posts.Posts;
import com.toyprj.toyman.service.posts.PostsService;
import com.toyprj.toyman.web.dto.PostsListResponseDto;
import com.toyprj.toyman.web.dto.PostsResponseDto;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor// final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable String id, Model model) {

        PostsResponseDto dto = postsService.findById(Long.parseLong(id));
        model.addAttribute("post", dto);

        return "posts-update";
    }



}
