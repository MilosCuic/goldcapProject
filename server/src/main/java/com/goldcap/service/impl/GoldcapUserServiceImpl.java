package com.goldcap.service.impl;

import com.goldcap.converter.UserDTOtoUser;
import com.goldcap.exception.EmailAlreadyTakenException;
import com.goldcap.exception.UsernameAlreadyTakenException;
import com.goldcap.model.GoldcapUser;
import com.goldcap.repository.GoldcapUserRepository;
import com.goldcap.service.GoldcapUserService;
import com.goldcap.web.dto.RegisterGoldcapUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoldcapUserServiceImpl implements GoldcapUserService {

    @Autowired
    private GoldcapUserRepository goldcapUserRepository;

    @Autowired
    private UserDTOtoUser dtOtoUser;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public GoldcapUserServiceImpl(GoldcapUserRepository goldcapUserRepository) {
        this.goldcapUserRepository = goldcapUserRepository;
    }

    @Override
    public GoldcapUser getById(Long id) {
        return goldcapUserRepository.getOne(id);
    }

    @Override
    public GoldcapUser save(RegisterGoldcapUserDTO userDTO) {



            //email has to be unique
            if (goldcapUserRepository.findByEmail(userDTO.getEmail()) != null) {
                throw new EmailAlreadyTakenException("There is already user with that email");
            }
            //username has to be unique
            if (goldcapUserRepository.findGoldcapUserByUsername(userDTO.getUsername()) != null){
                throw new UsernameAlreadyTakenException("Username already taken");
            }
            //make sure that password and confirm password are equal
            //custom exception

            GoldcapUser goldcapUser = dtOtoUser.convert(userDTO);

            System.out.println(goldcapUser);
        try {
            assert goldcapUser != null;
            goldcapUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return goldcapUserRepository.save(goldcapUser);
        }catch(Exception e){
            e.getMessage();
            e.printStackTrace();
            throw  new RuntimeException("Something went wrong");
        }
    }

    @Override
    public void deleteById(Long id) {
        goldcapUserRepository.deleteById(id);
    }

    @Override
    public Page<GoldcapUser> searchUsers(int pageNum, int pageSize , String field , String direction) {

        Pageable paging = PageRequest.of(pageNum , pageSize);

        if (direction.equalsIgnoreCase("DESC") && field != null){
            paging = PageRequest.of(pageNum , pageSize, Sort.by(field).descending());
        }
        if (direction.equalsIgnoreCase("ASC") && field != null){
            paging = PageRequest.of(pageNum , pageSize, Sort.by(field).ascending());
        }

        return goldcapUserRepository.findAll(paging);
    }

    @Override
    public List<GoldcapUser> findAll() {
        return goldcapUserRepository.findAll();
    }

    @Override
    public GoldcapUser findByUsername(String username) {
        return goldcapUserRepository.findGoldcapUserByUsername(username);
    }

    @Override
    public GoldcapUser findByEmail(String email) {
        return goldcapUserRepository.findByEmail(email);
    }
}
