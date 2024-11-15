package com.example.boardproj.controller;

import com.example.boardproj.dto.MemberShipDTO;
import com.example.boardproj.service.MemberShipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller @RequiredArgsConstructor @Log4j2
@RequestMapping("/user")
public class MemberShipController {

    // 의존성 부여
    private final MemberShipService memberShipService;

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signup(MemberShipDTO memberShipDTO) {

        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberShipDTO memberShipDTO, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, Model model) {

        log.info("컨트롤러로 들어온 memberShipDTO : " + memberShipDTO);

        // 유효성검사 진행
        if (bindingResult.hasErrors()) {
            log.info("컨트롤러 유효성검사로 들어온 memberShipDTO : " + memberShipDTO);
            return "user/signup";
        }

        // 예외처리 진행
        try {
            memberShipDTO =
                    memberShipService.saveMember(memberShipDTO);
        } catch (IllegalStateException e) {
            // 예외처리 발생시 메세지
            log.info("동일한 이메일이 있습니다.");
            log.info("동일한 이메일이 있습니다.");
            log.info("동일한 이메일이 있습니다.");

            model.addAttribute("msg", e.getMessage());
            return "user/signup";
        }

        // 일회성 데이더 저장하여 넘기기
        redirectAttributes.addFlashAttribute("memberShipDTO", memberShipDTO);
        return "redirect:/user/signup";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String login(MemberShipDTO memberShipDTO, Principal principal) {

        if (principal != null) {
            log.info("===========================");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("===========================");
        }

        return "user/login";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout() {

        return "redirect:/user/logout";
    }


}
