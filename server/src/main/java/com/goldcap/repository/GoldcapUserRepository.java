package com.goldcap.repository;

import com.goldcap.model.GoldcapUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GoldcapUserRepository  extends JpaRepository<GoldcapUser, Long> {

//    @Transactional
//    @Query(value="select from GoldcapUser as user where user.username = :username")
    GoldcapUser findGoldcapUserByUsername(String username);

    GoldcapUser getById(Long id);
    GoldcapUser findByEmail(String email);
}
