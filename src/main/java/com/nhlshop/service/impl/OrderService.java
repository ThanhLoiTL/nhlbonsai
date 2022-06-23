package com.nhlshop.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.nhlshop.entities.OrderEntity;
import com.nhlshop.repository.OrderRepository;
import com.nhlshop.service.IOrderService;

public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Collection<OrderEntity> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).getContent();
    }

    @Override
    public int totalItem() {
        return (int) orderRepository.count();
    }

    @Override
    public Collection<OrderEntity> findAll() {
        return null;
    }

    @Override
    public Optional<OrderEntity> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public OrderEntity saveOrUpdate(OrderEntity t) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

}
