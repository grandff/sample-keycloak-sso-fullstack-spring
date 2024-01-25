package com.kjm.auth.keycloakauth.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.kjm.auth.keycloakauth.auth.model.MemberRegsiterRequestDto;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    // 패스워드 일치여부 검사
    public boolean isPasswordConfirmed(MemberRegsiterRequestDto memberRegsiterRequestDto, BindingResult result) {
        if (!memberRegsiterRequestDto.getPassword().equals(memberRegsiterRequestDto.getPasswordConfirm())) {
            log.info("password not matched");
            result.rejectValue("passwordConfirm", "NotMatch", "비밀번호가 일치하지 않습니다.");
            return false;
        }
        return true;
    }
}
