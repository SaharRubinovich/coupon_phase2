package com.jb.coupon2.clr;

import com.jb.coupon2.beans.*;
import com.jb.coupon2.service.*;
import com.jb.coupon2.util.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Order(2)
public class ServicesTest implements CommandLineRunner {
    private final AdminService adminService;
    private final CustomerService customerService;
    private final CompanyService companyService;
    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        ClientService clientService = loginManager.login("admin@admin.com","admin",UserType.ADMIN.toString());
        System.out.println(clientService.getClass());
        Company company = Company.builder()
                .email("company@gamil.com")
                .name("Com")
                .password("123456")
                .build();
        adminService.addCompany(company);
        company.setPassword("1234");
        adminService.updateCompany(company);
        TablePrinter.print(adminService.getAllCompanies());
        Customer customer = Customer.builder()
                .email("Customer2@Gmail.com")
                .password("123456")
                .firstName("cus")
                .lastName("tomer")
                .build();
        adminService.addCustomer(customer);
        customer.setLastName("tumer");
        adminService.updateCustomer(customer);
        TablePrinter.print(adminService.getAllCustomers());
        TablePrinter.print(adminService.getOneCustomer(customer.getId()));


        clientService = loginManager.login(company.getEmail(), company.getPassword(), UserType.COMPANY.toString());
        System.out.println(clientService.getClass());
        Coupon coupon = Coupon.builder()
                .category(Category.GAMING)
                .title("Gaming coupon")
                .description("Test coupon")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(12))
                .amount(10)
                .price(33.57)
                .image("none")
                .build();
        companyService.setCompanyId(company.getId());
        companyService.addCoupon(coupon);
        TablePrinter.print(adminService.getOneCompany(company.getId()));
        coupon.setAmount(23);
        companyService.updateCoupon(coupon);
        TablePrinter.print(companyService.getCompanyCoupons());
        TablePrinter.print(companyService.getCompanyCoupons(coupon.getCategory()));
        TablePrinter.print(companyService.getCompanyDetails());


        clientService = loginManager.login(customer.getEmail(), customer.getPassword(), UserType.CUSTOMER.toString());
        System.out.println(clientService.getClass());
        customerService.setCustomerId(customer.getId());
        customerService.purchaseCoupon(coupon);
        TablePrinter.print(customerService.getCustomerCoupon());
        TablePrinter.print(customerService.getCustomerCoupon(100.0));
        TablePrinter.print(customerService.getCustomerCoupon(Category.GAMING));

        //adminService.deleteCustomer(customer.getId());
        //adminService.deleteCompany(company.getId());
    }
}
