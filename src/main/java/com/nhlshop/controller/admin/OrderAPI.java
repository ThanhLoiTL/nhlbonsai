package com.nhlshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhlshop.constant.OrderStatus;
import com.nhlshop.dto.Page;
import com.nhlshop.dto.ResponseObject;
import com.nhlshop.entities.OrderDetailEntity;
import com.nhlshop.entities.OrderEntity;
import com.nhlshop.service.IOrderDetailService;
import com.nhlshop.service.IOrderService;

@RestController
@RequestMapping("/admin/order")
public class OrderAPI {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll(@RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<OrderEntity> result = new Page<>();
        result.setPage(page);
        result.setList(orderService.findAll(pageable));
        result.setTotalpage((int) Math.ceil((double) (orderService.totalItem()) / limit));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Get orders successfully", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        try {
            OrderEntity order = orderService.findById(id).get();
            List<OrderDetailEntity> orderDetails = orderDetailService.findByOrder(order);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Get order successfully", orderDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "Cannot IMPLEMENTED", ""));
        }
    }

    @PutMapping("/cancel-order/{id}")
    public ResponseEntity<ResponseObject> cancelOrder(@PathVariable Long id) {
        try {
            OrderEntity order = orderService.findById(id).get();
            order.setStatus(OrderStatus.CANCEL.toString());
            orderService.saveOrUpdate(order);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Cancel order successfully", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "Cannot IMPLEMENTED", ""));
        }
    }
}
