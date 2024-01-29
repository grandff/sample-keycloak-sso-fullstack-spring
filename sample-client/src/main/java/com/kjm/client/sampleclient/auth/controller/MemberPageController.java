package com.kjm.client.sampleclient.auth.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // 로그인 화면으로 이동
    @GetMapping("/login")
    public String goToLogin(HttpServletRequest request, HttpServletResponse response, Model model)
            throws UnsupportedEncodingException {

        // 로그인만하고 다시 메인으로 가고 싶어도 resource 서버를 호출해야함
        // client에서 바로 auth하고 주고받아도 토큰은 받을지 몰라도 세션이 업데이트가 안됨
        // 따라서 무조건 리소스를 통해서 로그인을 하게 해야함.. 아마도
        this.webClient.get()
                .uri(fooApiUrl + "/user/detail")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
        return "redirect:/";

        // try {// redirect URL 알아보기
        // response.sendRedirect(
        // "http://localhost:8083/auth/realms/dev/protocol/openid-connect/auth?response_type=code&client_id=ssoClient-1&redirect_uri=http://localhost:8082/ui-one/member/auth&scope=openid&nonce=asb3");
        // // 여기도
        // } catch (IOException e) {
        // e.printStackTrace();
        // System.out.println("login오류창으로 이동");
        // }

        // return;
    }

    @GetMapping("/auth")
    public String loginAuth(HttpServletRequest request) throws UnsupportedEncodingException {
        String code = request.getParameter("code");
        String query = "code=" + URLEncoder.encode(code, "UTF-8");
        query += "&client_id=" + "ssoClient-1";
        query += "&client_secret=" + "KkGxR78OdKBdWgAmeecgfI7UaUgZ6buX";
        query += "&redirect_uri=" + "http://localhost:8082/ui-one/member/auth"; // 너님들이 설정해야할 redirect_uri. /login에서 호출한
                                                                                // 동일한 redirect_uri로 설정해야함. 안그러면 오류 발생.
        query += "&grant_type=client_credentials";

        webClient.post()
                .uri("http://localhost:8083/auth/realms/dev/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(query)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        // 토큰을 가지고 다시 user-info를 호출해서 로그인 정보 설정
        // 아래 security를 설정해주지 않으면 메인화면에서 로그인된거처럼 보이질 않음
        // 자꾸 403이 떠서.. resoruce 서버에 있는걸 호출함 그냥
        Map<String, Object> userInfo = this.webClient.get()
                .uri(fooApiUrl + "/user/detail")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        // Assuming the response contains the username
        log.info("test ... {}", userInfo.toString());
        String username = userInfo.get("data").toString();

        // Create a new authentication token
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, null);

        // Set the new authentication token to Security Context
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/";
    }

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
        return "redirect:http://localhost:8083/auth/realms/dev/protocol/openid-connect/logout?redirect_uri=http://localhost:8082/ui-one";
    }

    /// 프로필 화면 이동
    @GetMapping("/profile/view")
    public String viewUserProfile(Model model) {
        Map<String, Object> memberInfo = this.webClient.get()
                .uri(fooApiUrl + "/user/detail")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
        model.addAttribute("memberInfo", memberInfo);
        return "pages/member/profile/view";
    }

    /// 프로필 화면 이동
    @GetMapping("/profile/update")
    public String updateUserProfile(Model model) {
        Map<String, Object> memberInfo = this.webClient.get()
                .uri(fooApiUrl + "/user/detail")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        // 정보매핑하기
        Map<String, String> data = (Map<String, String>) memberInfo.get("data");
        MemberRegsiterRequestDto profileUpdateRequestDto = new MemberRegsiterRequestDto();
        profileUpdateRequestDto.setUsername(data.get("username"));
        profileUpdateRequestDto.setName(data.get("name"));
        profileUpdateRequestDto.setPhoneNo(data.get("phoneNo"));
        profileUpdateRequestDto.setCity(data.get("city"));
        profileUpdateRequestDto.setAddr(data.get("addr"));
        profileUpdateRequestDto.setBio(data.get("bio"));

        model.addAttribute("profileUpdateRequestDto", profileUpdateRequestDto);

        return "pages/member/profile/update";
    }

    /// 프로필 저장
    @PostMapping("/profile/save")
    public String saveUserProfile(@Valid MemberRegsiterRequestDto profileUpdateRequestDto, BindingResult result,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        // WebClient를 사용하여 POST 요청 보내기
        String url = fooApiUrl + "/member/profile/v1.0";
        Map<String, Object> resultMap = webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(profileUpdateRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
        System.out.println("resultMap : " + resultMap.toString());
        return "redirect:/member/profile/view";

    }

}
