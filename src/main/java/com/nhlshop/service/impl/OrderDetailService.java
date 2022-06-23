package com.nhlshop.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhlshop.entities.OrderDetailEntity;
import com.nhlshop.entities.OrderEntity;
import com.nhlshop.repository.OrderDetailRepository;
import com.nhlshop.service.IOrderDetailService;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;;

    @Override
    public List<OrderDetailEntity> findByOrder(OrderEntity order) {
        return orderDetailRepository.findByOrder(order);
    }

    @Override
    public Collection<OrderDetailEntity> findAll() {
        return null;
    }

    @Override
    public Optional<OrderDetailEntity> findById(Long id) {
        return null;
    }

    @Override
    public OrderDetailEntity saveOrUpdate(OrderDetailEntity orderDetail) {
        return orderDetailRepository.saveAndFlush(orderDetail);
    }

    @Override
    public void deleteById(Long id) {

    }

}
