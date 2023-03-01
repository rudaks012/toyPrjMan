package com.toyprj.toyman.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor// final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        System.out.println("Hello, World!");
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

}
