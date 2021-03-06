package com.jb.coupon2.service;

import com.jb.coupon2.beans.UserType;
import com.jb.coupon2.exception.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginManager {
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;


    public ClientService login(String email, String password, String userType) throws LoginException {
        ClientService clientService = null;

        if (userType.equals(UserType.ADMIN.toString()) && adminService.login(email, password)) {
            return clientService = adminService;
        } else if (companyService.login(email, password) && userType.equals(UserType.COMPANY.toString())) {
            return clientService = companyService;
        } else if (customerService.login(email, password) && userType.equals(UserType.CUSTOMER.toString())) {
            return clientService = customerService;
        } else {
            throw new LoginException("Email or Password incorrect, or user doesn't exist.");
        }
    }
}
