package com.nhlshop.controller.admin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/account")
public class AccountController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<Collection<UserEntity>> getAllUser() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<UserEntity> user = userService.findById(id);
        return user.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Find user successfully", user))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("FAILED", "Cannot find user with id " + id, ""));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> saveUser(@RequestBody UserDTO userDTO) {
        UserEntity userExist = userService.findByEmail(userDTO.getEmail());
        if (userExist != null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", "User already existed", ""));
        }
        UserEntity userEntity = userConverter.toEntity(userDTO);
        Set<RoleEntity> roles = new HashSet<>();
        for (String role : userDTO.getRoleName()) {
            roles.add(roleService.findByName(role));
        }
        userEntity.setRoles(roles);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        UserEntity user = userService.saveOrUpdate(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Insert user successfully", user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        Set<RoleEntity> roles = new HashSet<>();
        for (String role : userDTO.getRoleName()) {
            roles.add(roleService.findByName(role));
        }
        Optional<UserEntity> userExist = userService.findById(id)
                .map(user -> {
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    user.setPhone(userDTO.getPhone());
                    user.setAddress(userDTO.getAddress());
                    user.setRoles(roles);
                    return userService.saveOrUpdate(user);
                });
        return userExist.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Update user successfully", userExist))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("FAILED", "Cannot find user with id " + id, ""));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        Optional<UserEntity> userExist = userService.findById(id);
        if (userExist.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Delete user successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("FAILED", "Cannot find user with id " + id, ""));
    }
}
