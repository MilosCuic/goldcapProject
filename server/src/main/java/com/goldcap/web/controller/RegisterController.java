package com.goldcap.web.controller;

import com.goldcap.converter.UserDTOtoUser;
import com.goldcap.model.GoldcapUser;
import com.goldcap.service.GoldcapUserService;
import com.goldcap.service.impl.MapValidationErrorService;
import com.goldcap.web.dto.RegisterGoldcapUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private GoldcapUserService goldcapUserService;

    @Autowired
    private UserDTOtoUser dtoToUser;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterGoldcapUserDTO userDTO, BindingResult result){

        //TODO make sure password and confirm password match

        ResponseEntity<?> errorMap = mapValidationErrorService.validateResult(result);
        if (errorMap != null) {
            return errorMap;
        }

        GoldcapUser newUser = goldcapUserService.save(userDTO);

        return new ResponseEntity<>(
                newUser,
                HttpStatus.CREATED
        );
    }
}
