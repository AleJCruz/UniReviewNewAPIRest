package com.upc.unireview.interfaceservice;

import com.upc.unireview.entities.Role;

import java.util.List;

public interface RoleService {
    public void insert(Role role);
    public List<Role> list();
}
