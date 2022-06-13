package com.nhlshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhlshop.entities.CartDetailEntity;
import com.nhlshop.entities.CartEntity;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetailEntity, Long> {
    List<CartDetailEntity> findByCart(CartEntity cartEntity);
}
