package com.kjm.auth.keycloakauth.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "USERS_DETAILS") // FIXME 무조건 대문자로 써야했던걸로 기억하는데....
public class UserDetailVo {
    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "ADDR")
    private String addr;

    @Column(name = "CITY")
    private String city;

    @Column(name = "BIO")
    private String bio;
}
