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


    public ClientService login(String email, String password, UserType userType) throws LoginException {
        ClientService clientService = null;

        if (userType.equals(UserType.ADMIN) && adminService.login(email, password)) {
            return clientService = new AdminService();
        }
        else if (companyService.login(email, password) && userType.equals(UserType.COMPANY)) {
            return clientService = new CompanyService(email, password);
        }
        else if (customerService.login(email, password) && userType.equals(UserType.CUSTOMER)) {
            return clientService = new CustomerService(email, password);
        }
        else {
            throw new LoginException("Email or Password incorrect, or user doesn't exist.");
        }
    }
}
