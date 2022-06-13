package com.nhlshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhlshop.entities.CartDetailEntity;
import com.nhlshop.entities.CartEntity;
import com.nhlshop.repository.CartDetailRepository;
import com.nhlshop.service.ICartDetailService;

@Service
public class CartDetailService implements ICartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public CartDetailEntity saveOrUpdate(CartDetailEntity cartDetail) {
        return cartDetailRepository.saveAndFlush(cartDetail);
    }

    @Override
    public void deleteById(Long id) {
        cartDetailRepository.deleteById(id);
    }

    @Override
    public List<CartDetailEntity> findCartDetailByCart(CartEntity cartEntity) {
        return cartDetailRepository.findByCart(cartEntity);
    }

}
