package com.toyprj.toyman.web;

import com.toyprj.toyman.config.auth.LoginUser;
import com.toyprj.toyman.config.auth.dto.SessionUser;
import com.toyprj.toyman.service.posts.PostsService;
import com.toyprj.toyman.web.dto.PostsResponseDto;
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

    //기존에 (user != null)로 확인했던 세션 정보 값이 개선됨 -> 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있음
    //이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있음
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

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
