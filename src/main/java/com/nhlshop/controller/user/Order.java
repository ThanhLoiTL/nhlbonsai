package com.nhlshop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhlshop.constant.SystemConstant;
import com.nhlshop.dto.Page;
import com.nhlshop.dto.ResponseObject;
import com.nhlshop.entities.OrderEntity;
import com.nhlshop.entities.RoleEntity;
import com.nhlshop.entities.UserEntity;
import com.nhlshop.service.IOrderService;
import com.nhlshop.service.ISecurityService;
import com.nhlshop.service.IUserService;

@RestController
@RequestMapping("/order-user")
public class Order {
    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private IOrderService orderService;

    @GetMapping
    public ResponseEntity<ResponseObject> getOrderByShipper(@RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        try {
            boolean isFinded = false;
            UserEntity user = userService.findByEmail(securityService.findLoggedInUsername());
            for (RoleEntity role : user.getRoles()) {
                if (role.getRoleName().equals(SystemConstant.USER)) {
                    isFinded = true;
                }
            }
            if (!isFinded) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("FAILED", "Not Permission", ""));
            }
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<OrderEntity> result = new Page<>();
            result.setPage(page);
            result.setList(orderService.getOrderByUser(user, pageable));
            result.setTotalpage((int) Math.ceil((double) (orderService.totalItem()) / limit));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Get order successfully", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "Cannot Implemented", ""));
        }
    }
}
