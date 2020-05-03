package com.goldcap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RealmNameExistsException extends RuntimeException{

    public RealmNameExistsException(String message) {
        super(message);
    }
}
