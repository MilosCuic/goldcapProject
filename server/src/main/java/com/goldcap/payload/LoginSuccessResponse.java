package com.goldcap.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginSuccessResponse {

    private boolean success;
    private String token;

    public LoginSuccessResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
