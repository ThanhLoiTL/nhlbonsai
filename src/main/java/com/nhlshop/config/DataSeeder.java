package com.nhlshop.config;

import java.util.HashSet;
import java.util.Set;

import com.nhlshop.entities.RoleEntity;
import com.nhlshop.entities.UserEntity;
import com.nhlshop.repository.RoleRepository;
import com.nhlshop.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (roleRepository.findByRoleName("ADMIN") == null) {
            roleRepository.save(new RoleEntity("ADMIN"));
        }
        if (roleRepository.findByRoleName("USER") == null) {
            roleRepository.save(new RoleEntity("USER"));
        }
        if (roleRepository.findByRoleName("SHIPPER") == null) {
            roleRepository.save(new RoleEntity("SHIPPER"));
        }

        if (userRepository.findByEmail("admin@gmail.com") == null) {
            UserEntity admin = new UserEntity();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("123456"));
            admin.setFirstName("Phạm");
            admin.setLastName("Thanh Lợi");
            admin.setPhone("0394642410");
            admin.setAddress("Quảng Ngãi");
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName("ADMIN"));
            roles.add(roleRepository.findByRoleName("USER"));
            admin.setRoles(roles);
            userRepository.save(admin);
        }
        if (userRepository.findByEmail("member@gmail.com") == null) {
            UserEntity user = new UserEntity();
            user.setEmail("member@gmail.com");
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            user.setFirstName("Nguyễn");
            user.setLastName("Trọng Huy");
            user.setPhone("1234567");
            user.setAddress("Long An");
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName("USER"));
            user.setRoles(roles);
            userRepository.save(user);
        }
        if (userRepository.findByEmail("shipper@gmail.com") == null) {
            UserEntity shipper = new UserEntity();
            shipper.setEmail("shipper@gmail.com");
            shipper.setPassword(new BCryptPasswordEncoder().encode("123456"));
            shipper.setFirstName("Nguyễn");
            shipper.setLastName("Minh Nhật");
            shipper.setPhone("1234567");
            shipper.setAddress("Biên Hòa");
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName("SHIPPER"));
            shipper.setRoles(roles);
            userRepository.save(shipper);
        }
    }

}
