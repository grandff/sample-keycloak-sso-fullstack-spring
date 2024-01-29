package com.kjm.auth.keycloakauth.auth.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kjm.auth.keycloakauth.auth.model.MemberRegsiterRequestDto;
import com.kjm.auth.keycloakauth.auth.model.UserDetailVo;
import com.kjm.auth.keycloakauth.auth.model.UserVo;
import com.kjm.auth.keycloakauth.auth.service.AuthService;
import com.kjm.auth.keycloakauth.auth.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final UserService userSerivce;
    private final AuthService authService;
    private String resoureUrl = "http://localhost:8081/sso-resource-server";
    private static final Logger log = LoggerFactory.getLogger(MemberPageController.class);

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

    /* 
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
        String url = resoureUrl + "/member/v1.0";

        WebClient webClient = WebClient.builder()
                .baseUrl(resoureUrl)
                .build();

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
    }*/

        // 회원가입
        @PostMapping("/register")
        public String registerMember(@Valid MemberRegsiterRequestDto memberRegsiterRequestDto, BindingResult result,
                HttpSession session, Model model, RedirectAttributes redirectAttributes) {
            // 회원가입 유효성 error시 회원가입화면으로 return
            System.out.println("coem : registerMember");

            if (result.hasErrors()) {
                return "pages/member/register";
            }
    
            // 없는 데이터 생성
            memberRegsiterRequestDto.setBirthDate("2024-01-01");
            
            // 회원 등록
            UserVo userVo = userSerivce.adduserInfo(memberRegsiterRequestDto);
            UserDetailVo detailVo = userSerivce.addUserDetail(memberRegsiterRequestDto);
            System.out.println("UserVo : "+ userVo.toString());
            System.out.println("detailVo : "+ detailVo);

            if (userVo != null && detailVo != null){
                redirectAttributes.addFlashAttribute("registerResult", "Y"); 
            }
            else {
                redirectAttributes.addFlashAttribute("registerResult", "N");
            }        
            return "redirect:/";
        }
}
