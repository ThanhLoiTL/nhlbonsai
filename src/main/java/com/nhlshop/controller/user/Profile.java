package com.nhlshop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhlshop.dto.ResponseObject;
import com.nhlshop.entities.UserEntity;
import com.nhlshop.service.ISecurityService;
import com.nhlshop.service.IUserService;

@RestController
@RequestMapping()
public class Profile {
    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityService securityService;

    @GetMapping("/account/account_detail")
    public ResponseEntity<ResponseObject> getPagePlant() {
        UserEntity user = userService.findByEmail(securityService.findLoggedInUsername());
        return user != null ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Get profile successfully", user))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("FAILED", "Not login", ""));
    }
}
