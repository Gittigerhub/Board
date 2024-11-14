package com.example.boardproj.controller;

import com.example.boardproj.dto.BoardDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Controller
@Log4j2
public class SampleController {
    // 비동기식

    @GetMapping("/sample")
    public String sample() {

        return "sample";
    }

//    @ResponseBody  위에다가 붙이기도 함
    @PostMapping("/g")
    public @ResponseBody String f(@RequestBody BoardDTO boardDTO) {
        //              @RequestBody는 받는게 정해져있어서 그냥하면 에러뜸
        log.info(boardDTO);

        return boardDTO.getContent();
    }

    @PostMapping("/h")
    public @ResponseBody BoardDTO h(@RequestBody BoardDTO boardDTO) {
        log.info(boardDTO);

        return boardDTO;
    }

    // 대표적으로 ResponseEntity 이걸 쓰게될것임
    @PostMapping("/i")
    public ResponseEntity i(@RequestBody BoardDTO boardDTO) {
        log.info(boardDTO);

        if (boardDTO.getTitle().equals("신짱구")){
            return new ResponseEntity<String>("너님 잘못함", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<BoardDTO>(boardDTO, HttpStatus.OK);
    }

    @PostMapping("/z")
    public ResponseEntity z(BoardDTO boardDTO) {
        log.info(boardDTO);

        if (boardDTO.getTitle().equals("신짱구")){
            return new ResponseEntity<String>("너님 잘못함", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<BoardDTO>(boardDTO, HttpStatus.OK);
    }

    @GetMapping("/img")
    public String imgGet() {
        log.info("업로드");
        log.info("업로드");
        log.info("업로드");
        log.info("업로드");

        return "sampleimg";
    }

    @PostMapping("/img")
    // 이미지 타입은 MultipartFile이다. Html에서 enctype="multipart/form-data" 빠지면 널만 들어온다.
    public String imgPost(String str, MultipartFile mul) {
        log.info("업로드 포스트");
        log.info("업로드 포스트");
        log.info("업로드 포스트");
        log.info(str);
        log.info(mul.getOriginalFilename());

        try {
            if (mul != null && mul.getBytes() != null) {

                log.info(Arrays.toString(mul.getBytes()));
            }
        }
        catch (Exception e) {

        }

        File file = new File("C:\\upload\\a.png");
        try {
            mul.transferTo(file);
        }
        catch (IOException e) {

        }

        return "redirect:/img";
    }



}
