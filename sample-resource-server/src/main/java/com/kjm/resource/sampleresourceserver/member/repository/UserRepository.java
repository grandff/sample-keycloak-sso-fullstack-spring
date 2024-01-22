package com.kjm.resource.sampleresourceserver.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.resource.sampleresourceserver.member.model.UserVo;

public interface UserRepository extends JpaRepository<UserVo, String> {
    // 사용자 정보 조회
    Optional<UserVo> findByUsername(String username);
}
