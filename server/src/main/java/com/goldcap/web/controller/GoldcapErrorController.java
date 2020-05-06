package com.goldcap.web.controller;

import com.goldcap.exception.ForbiddenException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class GoldcapErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.FORBIDDEN.value()){
                throw new ForbiddenException("You dont have permission to access this resource");
            }
            //TODO add custom messages/exception for Http status 404 and 500
//            if (statusCode == HttpStatus.NOT_FOUND.value()) {
//                return "error404";
//            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                return "error500";
//            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "There has been error";
    }
}
