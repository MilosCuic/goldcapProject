package com.goldcap.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoldcapUserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;

}
