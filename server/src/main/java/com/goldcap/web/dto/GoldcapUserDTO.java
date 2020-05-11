package com.goldcap.web.dto;

import com.goldcap.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoldcapUserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private List<Role> roles;

}
