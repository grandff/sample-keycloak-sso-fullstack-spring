package com.kjm.auth.keycloakauth.auth.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.kjm.auth.keycloakauth.auth.model.MemberRegsiterRequestDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberPageController {
    private static final Logger log = LoggerFactory.getLogger(MemberPageController.class);

    // 회원가입 화면으로 이동
    @GetMapping("/register")
    public String goToRegister(MemberRegsiterRequestDto memberRegsiterRequestDto, HttpSession session) {
        session.removeAttribute("member");
        return "pages/member/register";
    }

    // 정보찾기 화면으로 이동
    @GetMapping("/find")
    public String goToFint() {
        return "pages/member/find";
    }

    // confirm 창으로 이동해서 입력 값 확인
    @GetMapping("/register/confirm")
    public String confirmRegister(HttpServletRequest request, HttpSession session,
            MemberRegsiterRequestDto memberRegsiterRequestDto,
            Model model) {

        // INFO 이걸 안해도 flash에서 설정한 이름과 동일하게 하면 가져올 수 있음
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null && inputFlashMap.get("member") != null) {
            model.addAttribute("member", inputFlashMap.get("member"));
        } else if (session.getAttribute("member") != null) {
            model.addAttribute("member", session.getAttribute("member"));
        } else {
            log.error("member attribute is not found");
            return "redirect:/member/register";
        }
        return "pages/member/confirm";
    }

}
