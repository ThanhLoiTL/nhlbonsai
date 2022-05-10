package com.nhlshop.service;

import com.nhlshop.entities.RoleEntity;

public interface IRoleService {
    RoleEntity findByName(String name);
}
