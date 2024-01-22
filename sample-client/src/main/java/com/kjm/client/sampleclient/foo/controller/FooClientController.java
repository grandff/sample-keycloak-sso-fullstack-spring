package com.kjm.client.sampleclient.foo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import com.kjm.client.sampleclient.foo.model.FooModel;

@Controller
public class FooClientController {

    // @Value("${resourceserver.api.url}")
    private String fooApiUrl = "http://localhost:8081/sso-resource-server";

    @Autowired
    private WebClient webClient;

    @GetMapping("/foos")
    public String getFoos(Model model) {
        List<FooModel> foos = this.webClient.get()
                .uri(fooApiUrl + "/api/foos/")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FooModel>>() {
                })
                .block();
        model.addAttribute("foos", foos);
        return "foos";
    }

    @GetMapping("/user/info")
    @ResponseBody
    public Map<String, Object> getUserInfo(Model model) {
        Map<String, Object> userInfo = this.webClient.get()
                .uri(fooApiUrl + "/user/info")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
        return userInfo;
    }

}
