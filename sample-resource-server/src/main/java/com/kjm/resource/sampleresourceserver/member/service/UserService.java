package com.kjm.resource.sampleresourceserver.member.service;

import java.util.Optional;

import com.kjm.resource.sampleresourceserver.member.model.UserDetailVo;
import com.kjm.resource.sampleresourceserver.member.model.UserVo;
import com.kjm.resource.sampleresourceserver.member.model.dto.MemberRequestDto;

public interface UserService {
    // 회원정보 조회
    Optional<UserVo> getUserInfo(String username);

    // 회원상세정보 조회
    Optional<UserDetailVo> getUserDetailInfo(String username);

    // 회원 등록 (마스터)
    UserVo adduserInfo(MemberRequestDto dto);

    // 회원 등록 (상세)
    UserDetailVo addUserDetail(MemberRequestDto dto);

    //업데이트
    UserDetailVo updateProfile(MemberRequestDto dto);

}
