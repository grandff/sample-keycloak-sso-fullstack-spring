package com.kjm.auth.keycloakauth.auth.model.dto;

import lombok.Data;

@Data
public class MemberRequestDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String name;
    private String phoneNo;
    private String addr;
    private String city;
    private String bio;
}
