package com.nhlshop.repository;

import com.nhlshop.entities.OrderDetailEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

}
