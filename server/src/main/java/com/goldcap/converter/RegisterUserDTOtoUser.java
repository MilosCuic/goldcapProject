package com.goldcap.converter;

import com.goldcap.model.GoldcapUser;
import com.goldcap.web.dto.RegisterGoldcapUserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegisterUserDTOtoUser implements Converter<RegisterGoldcapUserDTO, GoldcapUser> {

    @Override
    public GoldcapUser convert(RegisterGoldcapUserDTO source) {

        GoldcapUser goldcapUser = new GoldcapUser();

        goldcapUser.setFirstName(source.getFirstName());
        goldcapUser.setLastName(source.getLastName());
        goldcapUser.setUsername(source.getUsername());
        goldcapUser.setEmail(source.getEmail());
        goldcapUser.setPassword(source.getPassword());

        return goldcapUser;
    }

    public List<GoldcapUser> convert(List<RegisterGoldcapUserDTO> dtos) {
        List<GoldcapUser> users = new ArrayList<>();

        for (RegisterGoldcapUserDTO registerGoldcapUserDTO : dtos) {
            users.add(convert(registerGoldcapUserDTO));
        }
        return users;
    }
}
