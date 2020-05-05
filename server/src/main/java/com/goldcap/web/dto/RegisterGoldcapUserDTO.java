package com.goldcap.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterGoldcapUserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    @Email
    @NotNull(message = "email can't be null")
    @NotBlank(message = "email can't be blank")
    private String email;
    @NotNull(message = "username can't be null")
    @NotBlank(message = "username can't be blank")
    private String username;
    @NotNull(message = "password is required")
    @NotBlank(message = "password is required")
    private String password;
    @NotNull(message = "confirm password can't be null")
    @NotBlank(message = "confirm password can't be blank")
    private String confirmedPassword;

}
