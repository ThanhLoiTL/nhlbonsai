package com.nhlshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhlshop.entities.CartDetailEntity;
import com.nhlshop.entities.CartEntity;

@Service
public interface ICartDetailService {
    CartDetailEntity saveOrUpdate(CartDetailEntity cartDetail);

    void deleteById(Long id);

    List<CartDetailEntity> findCartDetailByCart(CartEntity cartEntity);

    void deleteByCart(CartEntity cart);
}
