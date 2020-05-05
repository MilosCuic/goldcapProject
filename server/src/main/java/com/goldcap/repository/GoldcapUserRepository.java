package com.goldcap.repository;

import com.goldcap.model.GoldcapUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldcapUserRepository  extends JpaRepository<GoldcapUser, Long> {


    GoldcapUser findGoldcapUserByUsername(String username);
    GoldcapUser getById(Long id);
    GoldcapUser findByEmail(String email);
}
