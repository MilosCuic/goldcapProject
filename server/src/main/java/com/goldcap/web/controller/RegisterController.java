package com.goldcap.web.controller;

import com.goldcap.converter.RegisterUserDTOtoUser;
import com.goldcap.converter.UserToGoldcapUserDTO;
import com.goldcap.events.OnRegistrationCompleteEvent;
import com.goldcap.exception.EmailAlreadyTakenException;
import com.goldcap.exception.UsernameAlreadyTakenException;
import com.goldcap.model.GoldcapUser;
import com.goldcap.model.VerificationToken;
import com.goldcap.service.GoldcapUserService;
import com.goldcap.service.impl.MapValidationErrorService;
import com.goldcap.validator.GoldcapUserValidator;
import com.goldcap.web.dto.RegisterGoldcapUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

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

    @Autowired
    ApplicationEventPublisher eventPublisher;

    private UserToGoldcapUserDTO toDto;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterGoldcapUserDTO userDTO ,
                                          BindingResult result ,
                                          HttpServletRequest request){

        ResponseEntity<?> errorMap = mapValidationErrorService.validateResult(result);
        if (errorMap != null) {
            return errorMap;
        }

        //TODO add this cases to error map
        if (goldcapUserService.findByEmail(userDTO.getEmail()) != null) {
            throw new EmailAlreadyTakenException("Email already taken");
        }
        if (goldcapUserService.findByUsername(userDTO.getUsername()) != null) {
            throw new UsernameAlreadyTakenException("Username already taken");
        }

        userValidator.validate(userDTO , result);

        try {
            GoldcapUser newUser = goldcapUserService.save(userDTO);

            String appUrl = request.getContextPath();

            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newUser,
                    request.getLocale(), appUrl));

            return new ResponseEntity<>(
                    newUser,
                    HttpStatus.CREATED
            );
        }catch (RuntimeException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(
                    "server error 500",
                    HttpStatus.NOT_ACCEPTABLE
            );
        }


    }

    @GetMapping(value = "/registrationConfirm")
    public ResponseEntity<?> confirmRegistration(WebRequest request ,
                                                 @RequestParam("token")String token){

        Locale locale = request.getLocale();

        VerificationToken verificationToken = goldcapUserService.getVerificationToken(token);
        if (verificationToken == null){
            return new ResponseEntity<>(
                    "Verification token is invalid",
                    HttpStatus.BAD_REQUEST
            );
        }

        GoldcapUser user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return new ResponseEntity<>(
                    "Verification token expired",
                    HttpStatus.BAD_REQUEST
            );
        }

        user.setVerified(true);
        goldcapUserService.saveVerifiedUser(user);

        return new ResponseEntity<>(
                "User successfully verified",
                HttpStatus.BAD_REQUEST
        );

    }


}
