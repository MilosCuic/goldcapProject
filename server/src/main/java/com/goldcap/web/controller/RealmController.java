package com.goldcap.web.controller;

import com.goldcap.exception.EntityIdException;
import com.goldcap.model.Realm;
import com.goldcap.service.RealmService;
import com.goldcap.service.impl.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@RestController
@RequestMapping("/realms")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class RealmController {

    private static final Logger logger = LoggerFactory.getLogger(RealmController.class);

    @Autowired
    private RealmService realmService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @GetMapping(value = "/{id}")
    ResponseEntity<?> getRealmById(@PathVariable Long id){
            Realm realm = realmService.getById(id);
            return new ResponseEntity<>(
                    realm,
                    HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveRealm(@Valid @RequestBody Realm realm , BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.validateResult(result);
        if (errorMap != null) {
            return errorMap;
        }

        Realm savedRealm = realmService.save(realm);

        return new ResponseEntity<>(
                savedRealm,
                HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Realm> editRealm(@Valid @RequestBody Realm realm , @PathVariable Long id , BindingResult result){

        if (realmService.getById(id) == null || !realm.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Realm editedRealm = realmService.save(realm);

        return new ResponseEntity<>(
                editedRealm,
                HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    ResponseEntity<List<Realm>> getRealmsPage(){

        List<Realm> realms = realmService.findAll();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("totalElements", Long.toString(realms.size()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(realms);
    }

    @GetMapping(value = "/{pageNum}/{pageSize}")
    ResponseEntity<List<Realm>> getRealmsPage(
            @PathVariable(name = "pageNum") int pageNum,
            @PathVariable(name = "pageSize") int pageSize){

        Page<Realm> realmPage = realmService.searchRealms(pageNum , pageSize);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("totalPages" , Integer.toString(realmPage.getTotalPages()) );
        responseHeaders.add("totalElements", Long.toString(realmPage.getTotalElements()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(realmPage.getContent());
    }

    @DeleteMapping(value="/{id}")
    ResponseEntity<?> delete(@PathVariable Long id){

        Realm toDelete = realmService.getById(id);

        realmService.delete(toDelete);
        return new ResponseEntity<>("Realm successfully deleted!" , HttpStatus.OK);
    }

    @PostMapping(value = "/deleteRealms"  , consumes="application/json")
    ResponseEntity<?> deleteMultipleRealms(@RequestBody List<Long> ids){
        logger.debug("Delete realms with {}", ids);


        if (ids != null && ids.size() != 0) {
            for (Long id : ids) {
                realmService.deleteById(id);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
