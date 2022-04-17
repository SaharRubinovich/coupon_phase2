package com.jb.coupon2.controller;

import com.jb.coupon2.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "company")
public class CompanyController {
    private final CompanyService companyService;
}
