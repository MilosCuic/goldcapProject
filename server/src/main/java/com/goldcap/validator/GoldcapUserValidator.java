package com.goldcap.validator;

import com.goldcap.web.dto.RegisterGoldcapUserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GoldcapUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> theClass) {
        return RegisterGoldcapUserDTO.class.equals(theClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RegisterGoldcapUserDTO user = (RegisterGoldcapUserDTO) target;

        if (user.getPassword().length() < 6) {
            //atribute password from pojo mofo
            errors.rejectValue("password" , "Lenght" , "Password must be at least 6 characters");
        }

        if (!user.getPassword().equals(user.getConfirmedPassword())){
            errors.rejectValue("confirmedPassword" , "Match" , "Password must match");
        }

    }
}
