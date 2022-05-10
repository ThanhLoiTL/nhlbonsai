package com.nhlshop.converter;

import com.nhlshop.dto.UserDTO;
import com.nhlshop.entities.UserEntity;

import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setAddress(userDTO.getAddress());
        return userEntity;
    }

    public UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setFirstName(userDTO.getFirstName());
        userDTO.setLastName(userDTO.getLastName());
        userDTO.setEmail(userDTO.getEmail());
        userDTO.setPassword(userDTO.getPassword());
        userDTO.setPhone(userDTO.getPhone());
        userDTO.setAddress(userDTO.getAddress());
        return userDTO;
    }
}
