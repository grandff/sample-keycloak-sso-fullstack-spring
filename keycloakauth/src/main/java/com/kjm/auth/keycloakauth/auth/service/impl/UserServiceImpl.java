package com.kjm.auth.keycloakauth.auth.service.impl;

import com.kjm.auth.keycloakauth.auth.model.MemberRegsiterRequestDto;
import com.kjm.auth.keycloakauth.auth.model.UserDetailVo;
import com.kjm.auth.keycloakauth.auth.model.UserVo;
import com.kjm.auth.keycloakauth.auth.model.dto.MemberRequestDto;
import com.kjm.auth.keycloakauth.auth.repository.UserDetailRepository;
import com.kjm.auth.keycloakauth.auth.repository.UserRepository;
import com.kjm.auth.keycloakauth.auth.service.UserService;

import java.util.Optional;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    // 회원정보 조회
    @Override
    public Optional<UserVo> getUserInfo(String username) {
        return userRepository.findByUsername(username);
    }

    // 회원상세정보 조회
    @Override
    public Optional<UserDetailVo> getUserDetailInfo(String username) {
        return userDetailRepository.findByUsername(username);
    }

    // 회원 등록 (마스터)
    @Override
    public UserVo adduserInfo(MemberRegsiterRequestDto dto) {
        UserVo vo = UserVo.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .build();

        return userRepository.save(vo);
    }

    // 회원 등록 (상세)
    @Override
    public UserDetailVo addUserDetail(MemberRegsiterRequestDto dto) {
        UserDetailVo vo = UserDetailVo.builder()
                .username(dto.getUsername())
                .name(dto.getName())
                .phoneNo(dto.getPhoneNo())
                .addr(dto.getAddr())
                .city(dto.getCity())
                .bio(dto.getBio())
                .build();
        return userDetailRepository.save(vo);
    }

    // 프로필 등록
    @Override
    public UserDetailVo updateProfile(MemberRegsiterRequestDto dto) {
        System.out.println("UserDetailVo updateProfile come : "+ dto.toString());
        UserDetailVo vo = UserDetailVo.builder()
                .username(dto.getUsername())
                .addr(dto.getAddr())
                .bio(dto.getBio())
                .build();
        return userDetailRepository.save(vo);
    }

}
