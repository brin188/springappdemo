package com.example.springappdemo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundException extends Exception {
    private Long errorCode;

    public ProductNotFoundException(Long errorCode, String message ) {
        super(message);
        this.errorCode = errorCode;
    }
}
