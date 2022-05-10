package com.nhlshop.service;

import java.util.List;
import java.util.Set;

import com.nhlshop.entities.RoleEntity;
import com.nhlshop.entities.UserEntity;

public interface IUserService extends IService<UserEntity> {
    UserEntity findByEmail(String email);

    List<UserEntity> findByRoles(Set<RoleEntity> roles);

}
