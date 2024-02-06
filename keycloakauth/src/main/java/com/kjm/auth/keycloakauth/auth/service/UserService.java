package com.kjm.auth.keycloakauth.auth.service;

import java.util.Optional;

import org.springframework.validation.BindingResult;
import com.kjm.auth.keycloakauth.auth.model.MemberRegsiterRequestDto;
import com.kjm.auth.keycloakauth.auth.model.UserDetailVo;
import com.kjm.auth.keycloakauth.auth.model.UserVo;
import com.kjm.auth.keycloakauth.auth.model.dto.MemberRequestDto;

public interface UserService {
    // 회원정보 조회
    Optional<UserVo> getUserInfo(String username);

    // 회원상세정보 조회
    Optional<UserDetailVo> getUserDetailInfo(String username);

    // 회원 등록 (마스터)
    UserVo adduserInfo(MemberRegsiterRequestDto dto);

    // 회원 등록 (상세)
    UserDetailVo addUserDetail(MemberRegsiterRequestDto dto);

    //업데이트
    UserDetailVo updateProfile(MemberRegsiterRequestDto dto);

    //비밀번호 찾기
    boolean isUsernameEmailConfirmed(MemberRequestDto memberRequestDto, BindingResult result);
    
}
