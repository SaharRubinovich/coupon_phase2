package com.jb.coupon2.controller;

import com.jb.coupon2.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "customer")
public class CustomerController {
    private final CustomerService customerService;
}
