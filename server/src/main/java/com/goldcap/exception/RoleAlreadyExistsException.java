package com.goldcap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleAlreadyExistsException extends RuntimeException{

    private String role;

    public RoleAlreadyExistsException(String role) {
        this.role = role;
    }
}
