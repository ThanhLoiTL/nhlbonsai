package com.nhlshop.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhlshop.entities.OrderEntity;
import com.nhlshop.entities.UserEntity;
import com.nhlshop.repository.OrderRepository;
import com.nhlshop.service.IOrderService;

@Service
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
    public OrderEntity saveOrUpdate(OrderEntity order) {
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<OrderEntity> getOrderByShipper(UserEntity shipper, Pageable pageable) {
        return orderRepository.findByShipper(shipper, pageable);
    }

    @Override
    public List<OrderEntity> getOrderByUser(UserEntity user, Pageable page) {
        return orderRepository.findByOrderUser(user, page);
    }

}
