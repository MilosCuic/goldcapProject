package com.goldcap;

import com.goldcap.repository.GoldcapUserRepository;
import com.goldcap.repository.OrderRepository;
import com.goldcap.service.impl.RealmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataLoader {

    @Autowired
    private GoldcapUserRepository userRepository;

    @Autowired
    private RealmServiceImpl realmService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void initData(){
//        String password = "asd";
//        String encoded = bCryptPasswordEncoder.encode(password);
//
//
//        for(int i=0 ; i< 5 ; i++) {
//            System.out.println("**************************");
//            System.out.println(encoded);
//            System.out.println(encoded.length());
//            System.out.println("**************************");
//        }

        String passWordpattern = "^.*(?=.{8,})(?=\\S+$)(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";
        String password1 = "19Milos92";
        String pass2 = "19milos92";

        System.out.println(password1.matches(passWordpattern));
        System.out.println(pass2.matches(passWordpattern));
    }
}
