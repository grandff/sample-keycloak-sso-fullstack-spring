package com.baeldung.client.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.client.web.model.FooModel;

@Controller
public class FooClientController {

    @Value("${resourceserver.api.url}")
    private String fooApiUrl;

    @Autowired
    private WebClient webClient;

    private static Logger log = LoggerFactory.getLogger(FooClientController.class);

    @GetMapping("/foos")
    public String getFoos(Model model) {
        log.info("[fooApiUrl 호출]"+fooApiUrl);
        fooApiUrl = "http://localhost:8081/sso-resource-server/api/foos/";

        List<FooModel> foos = this.webClient.get()
                .uri(fooApiUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FooModel>>() {
                })
                .block();
        model.addAttribute("foos", foos);
        return "foos";
    }
}
