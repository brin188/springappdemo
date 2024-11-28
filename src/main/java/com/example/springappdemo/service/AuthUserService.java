package com.example.springappdemo.service;

import com.example.springappdemo.dto.AuthUserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("AuthUserService")
public class AuthUserService implements UserService {

    RestTemplate restTemplate;

    public AuthUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean validateToken(String token) {
        AuthUserDto authUserDto = restTemplate.getForObject("http://localhost:8080/users/validate/" + token, AuthUserDto.class);
        if(authUserDto.getEmail() == null)
            return false;
        return true;
    }
}
