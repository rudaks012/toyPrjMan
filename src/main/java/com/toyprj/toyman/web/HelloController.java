package com.toyprj.toyman.web;

import com.toyprj.toyman.web.dto.HelloResponseDto;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello1")
    public String hello() {
        //왜 자꾸 react가 실행이 안되는지 모르겠다...?
        return "my-front/src/components/ListBoardComplnents.jsx";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }

}
