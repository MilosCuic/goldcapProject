package com.goldcap.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMessageResponse {

    private String message;

    public CustomMessageResponse(String message) {
        this.message = message;
    }
}
