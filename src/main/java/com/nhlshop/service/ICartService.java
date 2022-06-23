package com.nhlshop.service;

import org.springframework.stereotype.Service;

import com.nhlshop.entities.CartEntity;
import com.nhlshop.entities.UserEntity;

@Service
public interface ICartService {
    CartEntity createCart(CartEntity cart, UserEntity user);

    CartEntity saveOrUpdate(CartEntity cart);

    CartEntity calculateTotalMoney(CartEntity cart);

    CartEntity findByUser(UserEntity userEntity);
}
