package com.goldcap.service;

import com.goldcap.model.Role;

import java.util.List;

public interface RoleService {

    public Role getRoleByName(String name);
    public Role save(Role role);
    public List<Role> getAllRoles();

}
