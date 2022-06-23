package com.nhlshop.service;

import java.util.List;

import com.nhlshop.entities.OrderDetailEntity;
import com.nhlshop.entities.OrderEntity;

public interface IOrderDetailService extends IService<OrderDetailEntity> {
    List<OrderDetailEntity> findByOrder(OrderEntity order);
}
