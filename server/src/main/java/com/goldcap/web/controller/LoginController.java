package com.goldcap.web.controller;

import com.goldcap.converter.UserToGoldcapUserDTO;
import com.goldcap.payload.LoginRequest;
import com.goldcap.payload.LoginSuccessResponse;
import com.goldcap.security.TokenProvider;
import com.goldcap.service.GoldcapUserService;
import com.goldcap.service.impl.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.goldcap.util.Constants.TOKEN_PREFIX;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class LoginController {

    @Autowired
    private GoldcapUserService goldcapUserService;

    @Autowired
    private UserToGoldcapUserDTO toUserDTO;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MapValidationErrorService errorService;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                              BindingResult result){

        ResponseEntity<?> errorMap = errorService.validateResult(result);
        if (errorMap != null ) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new LoginSuccessResponse(true , jwt));

    }
}
