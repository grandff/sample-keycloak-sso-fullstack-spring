package com.kjm.auth.keycloakauth.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.auth.keycloakauth.auth.model.UserVo;

public interface UserRepository extends JpaRepository<UserVo, String> {
    // 사용자 정보 조회
    Optional<UserVo> findByUsername(String username);
}
