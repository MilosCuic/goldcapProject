package com.goldcap.service;

import com.goldcap.model.GoldcapUser;
import com.goldcap.model.VerificationToken;
import com.goldcap.web.dto.RegisterGoldcapUserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GoldcapUserService {

    public GoldcapUser getById(Long id);
    public GoldcapUser save(RegisterGoldcapUserDTO userDTO);
    public void deleteById(Long id);
    public Page<GoldcapUser> searchUsers(int pageNum , int pageSize , String field , String direction);
    public List<GoldcapUser> findAll();
    public GoldcapUser findByUsername(String username);
    public GoldcapUser findByEmail(String email);
    public GoldcapUser saveVerifiedUser(GoldcapUser goldcapUser);

    void createVerificationToken(GoldcapUser user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    public GoldcapUser getUserByToken(String verificationToken);

}
