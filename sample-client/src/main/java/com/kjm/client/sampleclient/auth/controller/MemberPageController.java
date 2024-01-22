package com.kjm.client.sampleclient.auth.controller;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.kjm.client.sampleclient.auth.model.MemberRegsiterRequestDto;
import com.kjm.client.sampleclient.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberPageController {
    private final AuthService authService;

    // @Value("${resourceserver.api.url}")
    private String fooApiUrl = "http://localhost:8081/sso-resource-server";

    @Autowired
    private WebClient webClient;

    private static final Logger log = LoggerFactory.getLogger(MemberPageController.class);

    // 회원가입 화면으로 이동
    @GetMapping("/register")
    public String goToRegister(MemberRegsiterRequestDto memberRegsiterRequestDto, HttpSession session) {
        session.removeAttribute("member");
        return "pages/member/register";
    }

    // 회원가입 입력값 검증
    @PostMapping("/register/validate")
    public String validateMemberRegister(@Valid MemberRegsiterRequestDto memberRegsiterRequestDto,
            BindingResult result, RedirectAttributes redirectAttributes, Model model, HttpSession session) {

        // 회원가입 유효성 error시 회원가입화면으로 return
        if (result.hasErrors() || !authService.isPasswordConfirmed(memberRegsiterRequestDto, result)) {
            log.info("fail... {}", result.toString());
            return "pages/member/register";
        }

        // 리다이렉트에 데이터 추가
        redirectAttributes.addFlashAttribute("member", memberRegsiterRequestDto);
        // 세션에 데이터 추가
        // FIXME confirm에서 새로고침하면 데이터가 사라지는 현상 방지
        // FIXME session에 저장하는게 좋은 방법일지...
        session.setAttribute("member", memberRegsiterRequestDto);

        return "redirect:/member/register/confirm";
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

    // 회원가입
    @PostMapping("/register")
    public String registerMember(@Valid MemberRegsiterRequestDto memberRegsiterRequestDto, BindingResult result,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        // 회원가입 유효성 error시 회원가입화면으로 return
        if (result.hasErrors()) {
            return "pages/member/register";
        }

        // 없는 데이터 생성
        memberRegsiterRequestDto.setBirthDate("2024-01-01");

        // WebClient를 사용하여 POST 요청 보내기
        String url = fooApiUrl + "/member/v1.0";

        Map<String, Object> resultMap = webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(memberRegsiterRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
        // 응답 처리
        log.info("result ... {}", resultMap.toString());
        String status = String.valueOf(resultMap.get("status")).replaceAll("null", "");

        if (status.equals("SUCCESS")) {
            redirectAttributes.addFlashAttribute("registerResult", "Y");
        } else {
            redirectAttributes.addFlashAttribute("registerResult", "N");
        }

        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:http://localhost:8083/auth/realms/dev/protocol/openid-connect/logout?post_logout_redirect_uri=http://localhost:8082/ui-one/login/oauth2/code/custom&client_id=ssoClient-1";
    }

}
