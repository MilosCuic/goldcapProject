package com.goldcap.repository;

import com.goldcap.model.Realm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealmRepository extends JpaRepository<Realm , Long> {

     Realm getById(Long id);
     Realm getByName(String name);
     Realm findByName(String name);
     Realm findAllByType(String type);
     Realm findAllByStatus(String status);
     Realm deleteByName(String name);
}
