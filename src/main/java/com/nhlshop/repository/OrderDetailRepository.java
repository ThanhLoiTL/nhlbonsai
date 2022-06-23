package com.nhlshop.repository;

import com.nhlshop.entities.OrderDetailEntity;
import com.nhlshop.entities.OrderEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    List<OrderDetailEntity> findByOrder(OrderEntity order);
}
