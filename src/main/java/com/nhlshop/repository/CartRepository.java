package com.nhlshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhlshop.entities.CartEntity;
import com.nhlshop.entities.UserEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUser(UserEntity userEntity);
}
