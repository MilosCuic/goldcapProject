package com.goldcap.events;

import com.goldcap.model.GoldcapUser;
import com.goldcap.service.GoldcapUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {


//    @Value("${spring.mail.host}")
//    private String host;
//    @Value("${spring.mail.port}")
//    private int port;
//    @Value("${spring.mail.username}")
//    private String username;
//    @Value("${spring.mail.password}")
//    private String password;

    @Autowired
    private GoldcapUserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    public void confirmRegistration(OnRegistrationCompleteEvent event){

        GoldcapUser user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user , token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl =
                event.getAppUrl() + "/register/registrationConfirm?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        //TODO Custom html response
        email.setText("You are succesfully registreted" + "\r\n" + "http://localhost:8080" + confirmationUrl);

        mailSender.send(email);
    }



}
