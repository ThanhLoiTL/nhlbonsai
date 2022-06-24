package com.nhlshop.repository;

import com.nhlshop.entities.RoleEntity;
import com.nhlshop.entities.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    List<UserEntity> findByRoles(RoleEntity role);
}