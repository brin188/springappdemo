package com.example.springappdemo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundExceptionDto {

    Long errorCode;
    String message;
}
