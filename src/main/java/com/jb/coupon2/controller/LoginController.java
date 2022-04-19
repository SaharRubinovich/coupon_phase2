package com.jb.coupon2.controller;

import com.jb.coupon2.beans.UserDetails;
import com.jb.coupon2.beans.UserType;
import com.jb.coupon2.exception.LoginException;
import com.jb.coupon2.security.JWTutil;
import com.jb.coupon2.service.ClientService;
import com.jb.coupon2.service.LoginManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class LoginController {
    private final LoginManager loginManager;
    private final JWTutil jwtUtil;

    @PostMapping(value = "login")
    public ResponseEntity<?> userLogin(@RequestParam String userEmail, @RequestParam String userPass, UserType userType)
            throws LoginException {
        Optional<ClientService> user = Optional.ofNullable(loginManager.login(userEmail, userPass, userType));
        if (user.isPresent()){
            ClientService clientService = user.get();
            UserDetails userDetails = new UserDetails(clientService.getId(), userEmail,userPass,String.valueOf(userType));
            return new ResponseEntity<>(jwtUtil.generateToken(userDetails), HttpStatus.OK);
        } else {
            throw new LoginException();
        }
    }
}
