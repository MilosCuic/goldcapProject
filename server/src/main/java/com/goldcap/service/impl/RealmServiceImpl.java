package com.goldcap.service.impl;

import com.goldcap.exception.EntityIdException;
import com.goldcap.exception.RealmNameExistsException;
import com.goldcap.model.Realm;
import com.goldcap.repository.RealmRepository;
import com.goldcap.service.RealmService;
import com.goldcap.web.controller.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RealmServiceImpl implements RealmService {

    private static final Logger logger = LoggerFactory.getLogger(RealmServiceImpl.class);


    private RealmRepository realmRepository;

    public RealmServiceImpl(RealmRepository realmRepository) {
        this.realmRepository = realmRepository;
    }

    @Override
    public Realm getById(Long id) {
            try{
                return realmRepository.getOne(id);
            }catch(Exception e){
                logger.error(String.valueOf(e));
                throw new EntityIdException("The realm with id: " + id + " does not exists");
            }

    }

    @Override
    public Realm findByName(String name) {
            return realmRepository.findByName(name);
    }

    @Override
    public Realm save(Realm realm) {

        if (realm.getId() != null) {
            throw new EntityIdException("There is something wrong , you are trying to persist already existing object");
        }
        
        try {
            return realmRepository.save(realm);
        }catch (Exception e){
            throw  new RealmNameExistsException("Realm name: " + realm.getName() + " already exists.");
        }

    }

    @Override
    public void delete(Realm realm) {
        realmRepository.delete(realm);
    }

    @Override
    public void deleteById(Long id) {
        try{
            realmRepository.deleteById(id);
        }catch (Exception e){
            throw new EntityIdException("The realm with id: " + id + " does not exists , and cant be deleted...");
        }

    }

    @Override
    public Page<Realm> searchRealms(int pageNum, int pageSize) {
        Page<Realm> allRealms = realmRepository.findAll(PageRequest.of(pageNum, pageSize));
        return allRealms;
    }

    @Override
    public List<Realm> findAll() {
        return realmRepository.findAll();
    }

}
