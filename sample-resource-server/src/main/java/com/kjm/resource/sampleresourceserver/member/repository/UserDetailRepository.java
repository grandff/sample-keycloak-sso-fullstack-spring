package com.kjm.resource.sampleresourceserver.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.resource.sampleresourceserver.member.model.UserDetailVo;

public interface UserDetailRepository extends JpaRepository<UserDetailVo, String> {
    // 사용자 정보 조회
    Optional<UserDetailVo> findByUsername(String username);
}
