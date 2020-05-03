package com.goldcap.service;

import com.goldcap.model.Order;
import com.goldcap.model.Realm;
import com.goldcap.repository.RealmRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RealmService {

    public Realm getById(Long id);
    public Realm save(Realm realm);
    public void delete(Realm realm);
    public void deleteById(Long id);
    public Page<Realm> searchRealms(int pageNum , int pageSize);
    public List<Realm> findAll();
    public Realm findByName(String name);


}
