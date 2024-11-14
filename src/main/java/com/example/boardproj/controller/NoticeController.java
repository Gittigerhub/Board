package com.example.boardproj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping("/register")
    public String noticeRegisterGet() {


        return "notice/register";
    }

    @PostMapping("/register")
    public String noticeRegisterPost() {


        return null;
    }

    @GetMapping("/read")
    public String noticeRead() {


        return "notice/read";
    }

    @GetMapping("/list")
    public String noticeList() {


        return "notice/list";
    }

    @GetMapping("/update")
    public String noticeUpdateGet() {


        return "notice/update";
    }

    @PostMapping("/update")
    public String noticeUpdatePost() {


        return null;
    }

    @PostMapping("/del")
    public String noticeDel() {


        return null;
    }

}
