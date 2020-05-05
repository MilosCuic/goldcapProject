package com.goldcap.converter;

import com.goldcap.model.GoldcapUser;
import com.goldcap.web.dto.GoldcapUserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToGoldcapUserDTO implements Converter<GoldcapUser , GoldcapUserDTO> {

    @Override
    public GoldcapUserDTO convert(GoldcapUser source) {
        if (source == null){
            return null;
        }
        GoldcapUserDTO goldcapUserDTO = new GoldcapUserDTO();

        goldcapUserDTO.setId(source.getId());
        goldcapUserDTO.setFirstName(source.getFirstName());
        goldcapUserDTO.setLastName(source.getLastName());
        goldcapUserDTO.setEmail(source.getEmail());
        goldcapUserDTO.setUsername(source.getUsername());

        return goldcapUserDTO;
    }

    public List<GoldcapUserDTO> convert(List<GoldcapUser> users) {

        List<GoldcapUserDTO> userDTOS = new ArrayList<>();

        for (GoldcapUser user : users) {
            userDTOS.add(convert(user));
        }

        return userDTOS;
    }
}
