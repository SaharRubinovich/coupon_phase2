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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
@CrossOrigin
public class LoginController {
    private final LoginManager loginManager;
    private final JWTutil jwtUtil;

    @PostMapping(value = "login")
    public ResponseEntity<?> userLogin(@RequestBody UserDetails userDetails)
            throws LoginException {
        Optional<ClientService> user = Optional.ofNullable(loginManager.login(
                userDetails.getUserEmail(), userDetails.getUserPass(), userDetails.getUserType()));
        if (user.isPresent()){
            ClientService clientService = user.get();
            UserDetails userData = new UserDetails(
                    clientService.getId(),userDetails.getUserEmail(),
                    userDetails.getUserPass(), userDetails.getUserType());
            return new ResponseEntity<>(jwtUtil.generateToken(userDetails), HttpStatus.OK);
        } else {
            throw new LoginException();
        }
    }
}
