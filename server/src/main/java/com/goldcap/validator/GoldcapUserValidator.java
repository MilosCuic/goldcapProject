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

    private final String passwordPattern = "^.*(?=\\S+$)(?=..*[0-9])(?=.*[A-Z]).*$";

//            ^                 # start-of-string
//            (?=.*[0-9])       # a digit must occur at least once
//            (?=.*[a-z])       # a lower case letter must occur at least once
//            (?=.*[A-Z])       # an upper case letter must occur at least once
//            (?=.*[@#$%^&+=])  # a special character must occur at least once
//            (?=\S+$)          # no whitespace allowed in the entire string
//            .{8,}             # anything, at least eight places though
//            $                 # end-of-string



    @Override
    public void validate(Object target, Errors errors) {

        RegisterGoldcapUserDTO user = (RegisterGoldcapUserDTO) target;

        if (!user.getPassword().matches(passwordPattern)){
            errors.rejectValue("password" , "Match" , "Password needs   to contain " +
                    "at least one UpperCase Letter and one Number");
        }

        if (user.getPassword().length() < 8) {
            errors.rejectValue("password" , "Lenght" , "Password must be at least 8 characters");
        }

        if (!user.getPassword().equals(user.getConfirmedPassword())){
            errors.rejectValue("confirmedPassword" , "Match" , "Password must match");
        }

    }
}
