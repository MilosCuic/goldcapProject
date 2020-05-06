package com.goldcap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="you don't have permission to access this page. ")
public class ForbiddenException extends RuntimeException{


    public ForbiddenException(String message) {
        super(message);
    }
}
