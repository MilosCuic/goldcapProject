package com.goldcap;

import com.goldcap.model.GoldcapUser;
import com.goldcap.model.Realm;
import com.goldcap.repository.GoldcapUserRepository;
import com.goldcap.repository.OrderRepository;
import com.goldcap.service.RealmService;
import com.goldcap.service.impl.RealmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostConstruct
    public void initData(){
    }
}
