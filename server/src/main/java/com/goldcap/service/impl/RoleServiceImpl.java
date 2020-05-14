package com.goldcap.service.impl;

import com.goldcap.exception.RoleAlreadyExistsException;
import com.goldcap.model.Role;
import com.goldcap.repository.RoleRepository;
import com.goldcap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role role) {

        if (roleRepository.findByName(role.getName()) != null){
            throw new RoleAlreadyExistsException("given role already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
