package com.jb.coupon2.clr;

import com.jb.coupon2.beans.Category;
import com.jb.coupon2.beans.Company;
import com.jb.coupon2.beans.Coupon;
import com.jb.coupon2.beans.Customer;
import com.jb.coupon2.repository.CompanyRepo;
import com.jb.coupon2.repository.CouponRepo;
import com.jb.coupon2.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@Order(1)
public class RepoTest implements CommandLineRunner {
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    CouponRepo couponRepo;

    @Override
    public void run(String... args) throws Exception {
        /*
        Category category1 = Category.builder()
                .name("FOOD")
                .build();
        Category category2 = Category.builder()
                .name("RESTAURANT")
                .build();
        Category category3 = Category.builder()
                .name("ELECTRICITY")
                .build();
        Category category4 = Category.builder()
                .name("VACATION")
                .build();

         */

        Company company = Company.builder()
                .email("Test@Test.com")
                .name("Test")
                .password("1234")
                .build();
        Customer customer = Customer.builder()
                .firstName("Sahar")
                .lastName("Rubinovich")
                .email("Sahar@gmail.com")
                .password("12345678")
                .build();
        companyRepo.save(company);
        customerRepo.save(customer);

        Coupon coupon = Coupon.builder()
                .amount(3)
                .companyId(company.getId())
                .category(Category.FOOD)
                .description("Test")
                .image("None")
                .price(33.3)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(12))
                .build();

        //  List<Category> categoryList = List.of(category1,category2,category3,category4);
        //categoryRepo.saveAll(categoryList);
        //coupon.setCompanyId(company.getId());
        company.setCoupons(List.of(coupon));
        companyRepo.saveAndFlush(company);
        //couponRepo.saveAndFlush(coupon);
    }
}
