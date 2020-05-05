package com.goldcap.service.impl;

import com.goldcap.model.GoldcapUser;
import com.goldcap.repository.GoldcapUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoldcapUserDetailsService implements UserDetailsService {


    @Autowired
    private GoldcapUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        GoldcapUser user = userRepository.findGoldcapUserByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User not found");

        return user;

//        return new User(user.getUsername , user.getPassowrd , authorities);


    }
    //from spring framework
    @Transactional
    public GoldcapUser loadGoldcapUserById(Long id) {
        GoldcapUser user = userRepository.getById(id);
        if (user == null)  throw new UsernameNotFoundException("User not found");

        return user;
    }
}
