package com.jb.coupon2.clr;

import com.jb.coupon2.beans.*;
import com.jb.coupon2.service.AdminService;
import com.jb.coupon2.util.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(3)
@RequiredArgsConstructor
public class TokenManagerTest implements CommandLineRunner {
    private final AdminService ADMIN_SERVICE;

    @Override
    public void run(String... args) throws Exception {
        Company company = Company.builder()
                .name("TokenTest")
                .password("12345")
                .email("TokenTest@Test.com")
                .build();
        ADMIN_SERVICE.addCompany(company);
        System.out.println(company.getId());
        company.setToken(new TokenManager(new UserDetails(company.getId(), company.getEmail(),
                company.getPassword(), UserType.COMPANY.toString())));
        ADMIN_SERVICE.updateCompany(company);
        TablePrinter.print(company);

        Customer customer = Customer.builder()
                .firstName("Test")
                .lastName("Token")
                .password("12345")
                .email("Tokenino@gmail.com")
                .build();
        ADMIN_SERVICE.addCustomer(customer);
        TablePrinter.print(customer);
        customer.setToken(new TokenManager(new UserDetails(
                customer.getId(), customer.getEmail(), customer.getPassword(), UserType.CUSTOMER.toString())));
        ADMIN_SERVICE.updateCustomer(customer);
        TablePrinter.print(customer);
    }
}
