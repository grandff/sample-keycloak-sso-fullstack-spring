package com.kjm.client.sampleclient.auth.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class AuthController {

    // @Value("${resourceserver.api.url}")
    private String fooApiUrl = "http://localhost:8081/sso-resource-server";

    @Autowired
    private WebClient webClient;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    /**
     * 로그인 테스트를 위한 컨트롤러
     * 로그인 후 전달받은 데이터를 화면에서 보여주기만 함
     * 토큰 정보는 백단에서만 주고 받게 되고, 클라이언트에서 보이는 정보는 이름, 전화번호 등
     * login-info는 client에 redirect uri에 등록되어 있어야함
     * 이건 직접 커스텀해서 확인하려고 하는거였음...........
     * 
     * @param param
     * @return
     */
    @GetMapping("/login-info")
    public String loginTest(HttpServletRequest request, HttpServletResponse response, Model model)
            throws ServletException, IOException {
        log.info("Redirect Start");

        // TEST print all request header
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            log.info("Parameter Name: " + paramName);

            String[] paramValues = request.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                String paramValue = paramValues[i];
                log.info("Parameter Value: " + paramValue);
            }
        }

        // 토큰 발급
        log.info("Token Create Start");
        String code = request.getParameter("code");
        String query = "code=" + URLEncoder.encode(code, "UTF-8");
        query += "&client_id=" + "ssoClient-1";
        query += "&client_secret=" + "ssoClientSecret-1";
        query += "&redirect_uri=" + "http://localhost:8082/ui-one/login-info"; // 너님들이 설정해야할 redirect_uri
        query += "&grant_type=authorization_code";
        String tokenJson = getHttpConnection("http://localhost:8083/auth/realms/sample/protocol/openid-connect/token",
                query);

        log.info("Token Create End. token is {}", tokenJson);

        // 발급한 토큰으로 회원 정보 요청
        log.info("User Info Request Start");
        log.info("User Info Request End");

        return "";
    }

    @GetMapping("/user/detail")
    public Map<String, Object> userInfoTest(HttpServletRequest request, HttpServletResponse response, Model model)
            throws ServletException, IOException {
        
        Map<String, Object> userInfo = this.webClient.get()
                .uri(fooApiUrl + "/user/detail")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
                return userInfo;
    }

    @GetMapping("/test1")
    public String auth(HttpServletRequest request, HttpServletResponse response, Model model)
            throws ServletException, IOException {
        // to do token save
        System.out.println("auth들어왓습니다잉???");

        // print all request parameter

        String code = request.getParameter("code");
        System.out.println("code=============" + URLEncoder.encode(code, "UTF-8"));

        String query = "code=" + URLEncoder.encode(code, "UTF-8");
        query += "&client_id=" + "ssoClient-1";
        query += "&client_secret=" + "ssoClientSecret-1";
        query += "&redirect_uri=" + "http://localhost:8082/ui-one/test1"; // 너님들이 설정해야할 redirect_uri
        query += "&grant_type=authorization_code";

        String tokenJson = getHttpConnection("http://localhost:8083/auth/realms/sample/protocol/openid-connect/token",
                query);
        System.out.println("tokenJson 먹었습니다~~~~~~~~~~");
        return tokenJson;
    }

    // TODO webclient로 수정해보자
    private String getHttpConnection(String uri, String param) throws ServletException, IOException {
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Accept", "application/json"); // 응답 형식 유형 설정
        conn.setDoOutput(true); // 콘텐츠를 보내는 데 연결이 사용되는지 확인
        try (OutputStream stream = conn.getOutputStream()) {
            try (BufferedWriter wd = new BufferedWriter(new OutputStreamWriter(stream))) {
                wd.write(param);// param은 /auth에서 날린 parameter들
            }
        }
        // int responseCode = conn.getResponseCode();
        // System.out.println(responseCode);
        String line;
        StringBuffer buffer = new StringBuffer();
        try (InputStream stream = conn.getInputStream()) {
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(stream))) {
                while ((line = rd.readLine()) != null) {
                    buffer.append(line);
                    buffer.append('\r');
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return buffer.toString(); // buffer에 json형태를 다 문자열로 바꿔서 view에 보여주고 있다
    }
}
