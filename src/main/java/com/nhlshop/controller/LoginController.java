package com.nhlshop.controller;

import com.nhlshop.config.JwtTokenProvider;
import com.nhlshop.dto.LoginRequest;
import com.nhlshop.dto.ResponseLogin;
import com.nhlshop.entities.UserEntity;
import com.nhlshop.service.IUserService;
import com.nhlshop.service.impl.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Transactional
public class LoginController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<ResponseLogin> authenticate(@Validated @RequestBody LoginRequest loginRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseLogin("FAILED", "Email or password not found!", ""));
        }
        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtTokenProvider.generateToken(userDetails);
        UserEntity user = userService.findByEmail(loginRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseLogin("OK", "Login successfully", user, jwt));
    }
}
