package com.nhlshop.controller.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhlshop.constant.OrderStatus;
import com.nhlshop.dto.OrderDTO;
import com.nhlshop.dto.ResponseObject;
import com.nhlshop.entities.CartEntity;
import com.nhlshop.entities.OrderEntity;
import com.nhlshop.entities.UserEntity;
import com.nhlshop.service.ICartService;
import com.nhlshop.service.ISecurityService;
import com.nhlshop.service.IUserService;

@RestController
@RequestMapping("/checkout")
public class Checkout {
    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private ICartService cartService;

    @PostMapping
    public ResponseEntity<ResponseObject> checkout(@RequestBody OrderDTO orderDTO) {
        if (!orderDTO.getAddress().trim().equals("") && !orderDTO.getReceiverName().trim().equals("")
                && !orderDTO.getReceiverPhone().trim().equals("")) {
            UserEntity user = userService.findByEmail(securityService.findLoggedInUsername());
            if (user != null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("FAILED", "Not login", ""));
            }
            OrderEntity order = new OrderEntity();
            order.setOrderUser(user);

            CartEntity cart = cartService.findByUser(user);
            order.setTotalMoney(cart.getTotalMoney());
            order.setAddress(orderDTO.getAddress());

            Date date = new Date();
            order.setOrderDate(date);

            order.setReceiverName(orderDTO.getReceiverName());
            order.setReceiverPhone(orderDTO.getReceiverPhone());

            order.setStatus(OrderStatus.PROCESSING.toString());

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Order successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("FAILED", "Cannot IMPLEMENTED", ""));
    }
}
