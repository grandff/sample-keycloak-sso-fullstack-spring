package com.kjm.auth.keycloakauth.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {
    @GetMapping("/sample")
    public String getFoos(Model model) {
        // 모델에 데이터를 추가할 수도 있습니다
        // model.addAttribute("foo", "bar");
        System.out.println("SampleController controller 호출 !");

        return "sample"; // foos.html을 호출합니다
    }
}
