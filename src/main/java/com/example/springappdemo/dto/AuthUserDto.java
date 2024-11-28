package com.example.springappdemo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthUserDto {
    private String name;
    private String email;
    private List<AuthRoleDto> roleList;
}
