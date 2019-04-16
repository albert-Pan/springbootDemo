package com.pw.example.demo3.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UserInfo {
    private Long id;
    private Integer age;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;

}
