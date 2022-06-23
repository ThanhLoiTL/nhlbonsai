package com.nhlshop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.nhlshop.entities.OrderDetailEntity;
import com.nhlshop.entities.OrderEntity;
import com.nhlshop.repository.OrderDetailRepository;
import com.nhlshop.service.IOrderDetailService;

public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;;

    @Override
    public List<OrderDetailEntity> findByOrder(OrderEntity order) {
        return orderDetailRepository.findByOrder(order);
    }

}
