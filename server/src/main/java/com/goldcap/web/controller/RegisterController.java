package com.goldcap.web.controller;

import com.goldcap.converter.RegisterUserDTOtoUser;
import com.goldcap.model.GoldcapUser;
import com.goldcap.service.GoldcapUserService;
import com.goldcap.service.impl.MapValidationErrorService;
import com.goldcap.validator.GoldcapUserValidator;
import com.goldcap.web.dto.RegisterGoldcapUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class RegisterController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private GoldcapUserService goldcapUserService;

    @Autowired
    private RegisterUserDTOtoUser dtoToUser;

    @Autowired
    private GoldcapUserValidator userValidator;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterGoldcapUserDTO userDTO, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.validateResult(result);
        if (errorMap != null) {
            return errorMap;
        }

        userValidator.validate(userDTO , result);

        GoldcapUser newUser = goldcapUserService.save(userDTO);

        return new ResponseEntity<>(
                newUser,
                HttpStatus.CREATED
        );
    }
}
