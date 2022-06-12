package com.jb.coupon2.clr;

import com.jb.coupon2.beans.*;
import com.jb.coupon2.service.*;
import com.jb.coupon2.util.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

//@Component
@RequiredArgsConstructor
@Order(2)
public class ServicesTest implements CommandLineRunner {
    private final AdminService adminService;
    private final CustomerService customerService;
    private final CompanyService companyService;
    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        ClientService clientService = loginManager.login("admin@admin.com", "admin", UserType.ADMIN.toString());
        System.out.println(clientService.getClass());
        Company company = Company.builder()
                .email("company@gamil.com")
                .name("Com")
                .password("123456")
                .build();
        Company company2 = Company.builder()
                .email("company2@gamil.com")
                .name("Com2")
                .password("123456")
                .build();
        adminService.addCompany(company);
        adminService.addCompany(company2);
        company.setPassword("1234");
        adminService.updateCompany(company);
        TablePrinter.print(adminService.getAllCompanies());
        Customer customer = Customer.builder()
                .email("Customer2@Gmail.com")
                .password("123456")
                .firstName("cus")
                .lastName("tomer")
                .build();
        Customer customer2 = Customer.builder()
                .email("Customer3@Gmail.com")
                .password("123456")
                .firstName("cus")
                .lastName("tomer")
                .build();
        adminService.addCustomer(customer);
        adminService.addCustomer(customer2);
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
        Coupon coupon2 = Coupon.builder()
                .category(Category.FOOD)
                .title("food coupon")
                .description("Test2 coupon")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(12))
                .amount(10)
                .price(44)
                .image("none")
                .build();
        Coupon coupon3 = Coupon.builder()
                .category(Category.COSMETICS)
                .title("cosmetic coupon")
                .description("Test3 coupon")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(12))
                .amount(30)
                .price(120)
                .image("none")
                .build();
        companyService.setCompanyId(company.getId());
        companyService.addCoupon(coupon);
        companyService.setCompanyId(company2.getId());
        companyService.addCoupon(coupon2);
        companyService.addCoupon(coupon3);
        TablePrinter.print(adminService.getOneCompany(company.getId()));
        coupon.setAmount(23);
        companyService.updateCoupon(coupon);
        TablePrinter.print(companyService.getCompanyCoupons());
        TablePrinter.print(companyService.getCompanyCoupons(coupon3.getCategory()));
        TablePrinter.print(companyService.getCompanyDetails());


        clientService = loginManager.login(customer.getEmail(), customer.getPassword(), UserType.CUSTOMER.toString());
        System.out.println(clientService.getClass());
        customerService.setCustomerId(customer.getId());
        customerService.purchaseCoupon(coupon);
        customerService.setCustomerId(customer2.getId());
        customerService.purchaseCoupon(coupon2);
        customerService.purchaseCoupon(coupon3);
        TablePrinter.print(customerService.getCustomerCoupon());
        TablePrinter.print(customerService.getCustomerCoupon(100.0));
        TablePrinter.print(customerService.getCustomerCoupon(Category.FOOD));
        /*
        adminService.deleteCustomer(customer.getId());
        adminService.deleteCustomer(customer2.getId());
        adminService.deleteCompany(company.getId());
        adminService.deleteCompany(company2.getId());
         */
    }
}
