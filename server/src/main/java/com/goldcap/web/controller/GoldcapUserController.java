package com.goldcap.web.controller;

import com.goldcap.converter.UserToGoldcapUserDTO;
import com.goldcap.model.GoldcapUser;
import com.goldcap.service.GoldcapUserService;
import com.goldcap.web.dto.GoldcapUserDTO;
import com.goldcap.web.dto.RegisterGoldcapUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
@RolesAllowed({ "ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class GoldcapUserController {

    @Autowired
    private GoldcapUserService goldcapUserService;

    @Autowired
    private UserToGoldcapUserDTO toUserDTO;



    @PostMapping
    public ResponseEntity<GoldcapUserDTO> addUser(@RequestBody RegisterGoldcapUserDTO userDTO){

        if (userDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        GoldcapUser user = goldcapUserService.save(userDTO);

        return new ResponseEntity<>(
                toUserDTO.convert(user),
                HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<GoldcapUserDTO> editUser(@PathVariable Long id, @RequestBody RegisterGoldcapUserDTO userDTO){

        if (userDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(goldcapUserService.getById(id) == null || !id.equals(userDTO.getId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        GoldcapUser user = goldcapUserService.save(userDTO);

        return new ResponseEntity<>(
                toUserDTO.convert(user),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GoldcapUserDTO> getUserById(@PathVariable Long id){

        if (goldcapUserService.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        GoldcapUser user = goldcapUserService.getById(id);

        return new ResponseEntity<>(
                toUserDTO.convert(user),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        goldcapUserService.deleteById(id);
        return new ResponseEntity<>("User with id: " + id + " successfully deleted!" , HttpStatus.OK);
    }

    @GetMapping(value = "/{pageNum}/{pageSize}")
    public ResponseEntity<List<GoldcapUserDTO>> getUserById
            (@PathVariable int pageNum,
             @PathVariable int pageSize,
             @RequestParam(required=false , defaultValue = "id") String field,
             @RequestParam(required=false , defaultValue = "ASC") String direction,
             Principal principal){


        Page<GoldcapUser> userPage = null;

        userPage = goldcapUserService.searchUsers(pageNum , pageSize , field , direction);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("totalPages", Integer.toString(userPage.getTotalPages()) );
        responseHeaders.add("totalElements", Long.toString(userPage.getTotalElements()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(toUserDTO.convert(userPage.getContent()));
    }


}
