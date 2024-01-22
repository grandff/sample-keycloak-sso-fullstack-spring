package com.kjm.client.sampleclient.auth.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class MemberRegsiterRequestDto {
    @NotEmpty(message = "아이디는 필수입니다.")
    private String username;

    private String password;
    private String passwordConfirm;
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    private String phoneNo;
    @Size(min = 10, message = "주소는 최소 10자리 이상입니다.")
    private String addr;
    private String city;

    @NotEmpty(message = "자기소개는 필수입니다.")
    private String bio;
}
