package com.kjm.auth.keycloakauth.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjm.auth.keycloakauth.auth.model.UserDetailVo;


public interface UserDetailRepository extends JpaRepository<UserDetailVo, String> {
    // 사용자 정보 조회
    Optional<UserDetailVo> findByUsername(String username);
}
