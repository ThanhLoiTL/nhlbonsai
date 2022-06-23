package com.nhlshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhlshop.entities.CartDetailEntity;
import com.nhlshop.entities.CartEntity;
import com.nhlshop.entities.UserEntity;
import com.nhlshop.repository.CartRepository;
import com.nhlshop.service.ICartDetailService;
import com.nhlshop.service.ICartService;

@Service
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ICartDetailService cartDetailService;

    @Override
    public CartEntity saveOrUpdate(CartEntity cart) {
        return cartRepository.saveAndFlush(cart);
    }

    @Override
    public CartEntity findByUser(UserEntity userEntity) {
        return cartRepository.findByUser(userEntity);
    }

    @Override
    public CartEntity createCart(CartEntity cart, UserEntity user) {
        cart = new CartEntity();
        cart.setUser(user);
        cart.setTotalMoney(0L);
        return this.saveOrUpdate(cart);
    }

    @Override
    public CartEntity calculateTotalMoney(CartEntity cart) {
        Long totalMoney = 0L;
        List<CartDetailEntity> cartUpdateDetails = cartDetailService.findCartDetailByCart(cart);
        for (CartDetailEntity detail : cartUpdateDetails) {
            totalMoney += detail.getProduct().getPrice() * detail.getQuantity();
        }
        cart.setTotalMoney(totalMoney);
        return this.saveOrUpdate(cart);
    }

    @Override
    public void deleteByUser(UserEntity userEntity) {
        cartRepository.deleteByUser(userEntity);
    }

}
