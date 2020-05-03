package com.goldcap.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

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
    @Size(min = 6 , max = 30)
    @NotNull(message = "password is required")
    @NotBlank(message = "password is required")
    private String password;
    @Size(min = 6 , max = 30)
    @NotNull(message = "confirm password can't be null")
    @NotBlank(message = "confirm password can't be blank")
    private String confirmedPassword;

}
