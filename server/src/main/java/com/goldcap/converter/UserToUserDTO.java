package com.goldcap.converter;

import com.goldcap.model.GoldcapUser;
import com.goldcap.web.dto.RegisterGoldcapUserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToUserDTO implements Converter<GoldcapUser , RegisterGoldcapUserDTO> {

    @Override
    public RegisterGoldcapUserDTO convert(GoldcapUser source) {
        if (source == null){
            return null;
        }
        RegisterGoldcapUserDTO registerGoldcapUserDTO = new RegisterGoldcapUserDTO();

        registerGoldcapUserDTO.setId(source.getId());
        registerGoldcapUserDTO.setFirstName(source.getFirstName());
        registerGoldcapUserDTO.setLastName(source.getLastName());
        registerGoldcapUserDTO.setEmail(source.getEmail());
        registerGoldcapUserDTO.setUsername(source.getUsername());

        return registerGoldcapUserDTO;
    }

    public List<RegisterGoldcapUserDTO> convert(List<GoldcapUser> users) {

        List<RegisterGoldcapUserDTO> userDTOS = new ArrayList<>();

        for (GoldcapUser user : users) {
            userDTOS.add(convert(user));
        }

        return userDTOS;
    }
}
