package com.nhlshop.service;

import java.util.List;

import com.nhlshop.entities.RoleEntity;
import com.nhlshop.entities.UserEntity;

public interface IUserService extends IService<UserEntity>, IPageService<UserEntity> {
    UserEntity findByEmail(String email);

    List<UserEntity> findByRoles(RoleEntity roles);

}
