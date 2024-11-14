package com.example.boardproj.controller;

import com.example.boardproj.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
public class RestSampleController {
    // 비동기식

    @GetMapping("/a")
    public String a(String a) {

        log.info(a);

        return "홍길동";
    }

    @PostMapping("/c")
    public String c(String title) {

        log.info(title);
        title = title + "환영합니다.";

        return "title";
    }

    @PutMapping("/d")
    public String d(String title) {
        log.info(title);

        title = title + 33333;

        return "title";
    }

    @GetMapping("/e")
    public String e(BoardDTO boardDTO) {
        log.info(boardDTO);

        return boardDTO.getContent();
    }

    @PostMapping("/f")
    public String f(BoardDTO boardDTO) {
        log.info(boardDTO);

        return boardDTO.getContent();
    }


}
