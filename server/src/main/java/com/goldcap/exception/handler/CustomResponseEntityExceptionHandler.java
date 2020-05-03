package com.goldcap.exception.handler;

import com.goldcap.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
//global exception handling for controllers for wired exceptions.
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleIdException(EntityIdException ex , WebRequest request){
        CustomMessageResponse response = new CustomMessageResponse(ex.getMessage());
        return new ResponseEntity(response , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRealmNameAlreadyExistsException(RealmNameExistsException ex , WebRequest request){
        CustomMessageResponse response = new CustomMessageResponse(ex.getMessage());
        return new ResponseEntity(response , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex, WebRequest request){
        CustomMessageResponse response = new CustomMessageResponse(ex.getMessage());
        return new ResponseEntity(response , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex, WebRequest request){
        CustomMessageResponse response = new CustomMessageResponse(ex.getMessage());
        return new ResponseEntity(response , HttpStatus.BAD_REQUEST);
    }

}
