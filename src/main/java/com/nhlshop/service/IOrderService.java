package com.nhlshop.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.nhlshop.entities.OrderEntity;
import com.nhlshop.entities.UserEntity;

public interface IOrderService extends IPageService<OrderEntity>, IService<OrderEntity> {
    List<OrderEntity> getOrderByShipper(UserEntity shipper, Pageable pageable);
}
