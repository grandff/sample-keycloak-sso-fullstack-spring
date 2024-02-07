package com.kjm.auth.keycloakauth.auth.controller;

import java.util.Map;
import java.util.Optional;

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
import com.kjm.auth.keycloakauth.auth.model.dto.MemberRequestDto;
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

        //로그인 후 어느화면으로 이동할건지 확인필요(24.01.29 임미연)
        return "redirect:http://localhost:8083/auth/";
    }


    //아이디 이메일 확인  
    @PostMapping("/findpwd/validate")
    public String validateFindPassword(@Valid MemberRequestDto memberRequestDto, BindingResult result, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        log.info("/findpwd/validate come! "+ memberRequestDto.toString() );
        // 회원가입 유효성 error시 회원가입화면으로 return
        if (result.hasErrors() || !userSerivce.isUsernameEmailConfirmed(memberRequestDto, result)) {
            log.info("fail... {}", result.toString());
            model.addAttribute("errorMessage", "아이디(로그인 전용 아이디) 또는 이메일을 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.");
            return "pages/member/findpwd";
        }

        // 정보 가져오기    
        Optional<UserVo> uservo = userSerivce.getUserInfo(memberRequestDto.getUsername());
        if (!uservo.isPresent()) {
            model.addAttribute("errorMessage", "관리자에게 다시 확인해주세요.");
            return "pages/member/findpwd";
        }
        
        UserVo user = uservo.get();
        model.addAttribute("member", user);
        session.setAttribute("member", uservo);

        return "pages/member/confirmpwd";
    }
}
