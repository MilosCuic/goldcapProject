package com.goldcap.web.controller;

import com.goldcap.model.Role;
import com.goldcap.service.RoleService;
import com.goldcap.service.impl.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

import static com.goldcap.util.Constants.ROLE_SUPER_ADMIN;

@RestController
@CrossOrigin(origins = "*" , allowedHeaders = "*")
@RequestMapping("/admin/roles")
@RolesAllowed({ROLE_SUPER_ADMIN})
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?> addRole(@Valid @RequestBody Role role , BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.validateResult(result);
        if (errorMap != null) {
            return errorMap;
        }

        Role savedRole = roleService.save(role);

        return new ResponseEntity<>(
                savedRole,
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles(){

        List<Role> roles = roleService.getAllRoles();

        return new ResponseEntity<>(
                roles,
                HttpStatus.CREATED);
    }
}
