package com.nhlshop.controller;

import java.util.HashSet;
import java.util.Set;

import com.nhlshop.converter.UserConverter;
import com.nhlshop.dto.ResponseObject;
import com.nhlshop.dto.UserDTO;
import com.nhlshop.entities.RoleEntity;
import com.nhlshop.entities.UserEntity;
import com.nhlshop.service.IRoleService;
import com.nhlshop.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private IRoleService roleService;

    @PostMapping
    public ResponseEntity<ResponseObject> authenticate(@RequestBody UserDTO userDTO)
            throws Exception {
        UserEntity userExist = userService.findByEmail(userDTO.getEmail());
        if (userExist != null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "Email already existed", ""));
        }
        UserEntity userEntity = userConverter.toEntity(userDTO);
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleService.findByName("USER"));
        userEntity.setRoles(roles);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userService.saveOrUpdate(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Register successfully", ""));
    }
}
