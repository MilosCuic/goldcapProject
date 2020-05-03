package com.goldcap.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidLoginResponse {

    private String username;
    private String password;

    private String customMessage;

    public InvalidLoginResponse() {
        this.username = "Invalid username";
        this.password = "Invalid password";
    }

    public InvalidLoginResponse(String customMessage){
        this.customMessage = customMessage;
    }
}
