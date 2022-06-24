package com.nhlshop.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.nhlshop.entities.RoleEntity;
import com.nhlshop.entities.UserEntity;
import com.nhlshop.repository.UserRepository;
import com.nhlshop.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserEntity> findByRoles(RoleEntity roles) {
        return userRepository.findByRoles(roles);
    }

    @Override
    public Collection<UserEntity> findAll() {
        return (Collection<UserEntity>) userRepository.findAll();
    }

    @Override
    public Collection<UserEntity> findAll(Pageable pageable) {
        return (Collection<UserEntity>) userRepository.findAll(pageable).getContent();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return (Optional<UserEntity>) userRepository.findById(id);
    }

    @Override
    public UserEntity saveOrUpdate(UserEntity user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public int totalItem() {
        return (int) userRepository.count();
    }

}
