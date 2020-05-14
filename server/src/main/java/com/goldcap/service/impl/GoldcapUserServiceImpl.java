package com.goldcap.service.impl;

import com.goldcap.converter.RegisterUserDTOtoUser;
import com.goldcap.model.GoldcapUser;
import com.goldcap.model.VerificationToken;
import com.goldcap.repository.GoldcapUserRepository;
import com.goldcap.repository.VerificationTokenRepository;
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
    private RegisterUserDTOtoUser dtOtoUser;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public GoldcapUserServiceImpl(GoldcapUserRepository goldcapUserRepository) {
        this.goldcapUserRepository = goldcapUserRepository;
    }

    @Override
    public GoldcapUser getById(Long id) {
        return goldcapUserRepository.getOne(id);
    }

    @Override
    public GoldcapUser save(RegisterGoldcapUserDTO userDTO) {
        try {
            GoldcapUser goldcapUser = dtOtoUser.convert(userDTO);

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

    @Override
    public GoldcapUser saveVerifiedUser(GoldcapUser goldcapUser) {
        return goldcapUserRepository.save(goldcapUser);
    }

    //TODO napraviti u service interfacu
    @Override
    public GoldcapUser getUserByToken(String verificationToken) {
        GoldcapUser user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
    @Override
    public void createVerificationToken(GoldcapUser user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
}
