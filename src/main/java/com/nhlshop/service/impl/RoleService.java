package com.nhlshop.service.impl;

import com.nhlshop.entities.RoleEntity;
import com.nhlshop.repository.RoleRepository;
import com.nhlshop.service.IRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleEntity findByName(String name) {
        return roleRepository.findByRoleName(name);
    }
}
