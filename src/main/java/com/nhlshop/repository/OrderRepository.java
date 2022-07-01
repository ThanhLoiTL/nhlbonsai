package com.nhlshop.repository;

import com.nhlshop.entities.OrderEntity;
import com.nhlshop.entities.UserEntity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByShipper(UserEntity shipper, Pageable pageable);

    List<OrderEntity> findByOrderUser(UserEntity user, Pageable pageable);
}
