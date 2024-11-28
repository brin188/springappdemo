package com.example.springappdemo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRoleDto {
    private Long id;
    private String value;
    private boolean deleted;
}
