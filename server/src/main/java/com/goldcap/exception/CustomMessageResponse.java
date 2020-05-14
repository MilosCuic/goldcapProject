package com.goldcap.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMessageResponse {

    private String message;
    private String email;
    private String username;

    public CustomMessageResponse(String message) {
        this.message = message;
    }

    public CustomMessageResponse(String email , String username) {
        this.email = email;
        this.username = username;
    }
}
