package com.jb.coupon2.service;

import com.jb.coupon2.beans.Category;
import com.jb.coupon2.beans.Coupon;
import com.jb.coupon2.beans.Customer;
import com.jb.coupon2.exception.LoginException;
import com.jb.coupon2.exception.PurchaseException;
import com.jb.coupon2.repository.CustomerRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerService extends ClientService{
    private int customerId;

    public CustomerService(String email, String password) {
        this.customerId = customerRepo.findCustomerByEmailAndPassword(email, password).getId();
    }

    public void purchaseCoupon(Coupon coupon) throws PurchaseException {
        if (couponRepo.checkPurchase(this.customerId, coupon.getId()) > 0) {
            throw new PurchaseException("Coupon already purchased by this account.");
        }
        if (couponRepo.getById(coupon.getId()).getAmount() == 0) {
            throw new PurchaseException("Coupon out of stock");
        }
        if (couponRepo.getById(coupon.getId()).getEndDate().isBefore(LocalDate.now())) {
            throw new PurchaseException("Coupon expired");
        }
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepo.saveAndFlush(coupon);
        Customer customer = customerRepo.getById(this.customerId);
        customer.getCoupons().add(coupon);
        customerRepo.saveAndFlush(customer);
    }
    public List<Coupon> getCustomerCoupon() {
        return customerRepo.getById(this.customerId).getCoupons();
    }
    public List<Coupon> getCustomerCoupon(Category category) {
        List<Coupon> couponList = getCustomerCoupon();
        return couponList.stream().filter(
                        couponCategory -> category.ordinal() == couponCategory.getCategory().ordinal())
                .collect(Collectors.toList());
    }
    public List<Coupon> getCustomerCoupon(double maxPrice) {
        return getCustomerCoupon().stream().filter(
                coupon -> coupon.getPrice() <= maxPrice
        ).collect(Collectors.toList());
    }
    public Customer getCustomerDetails() {
        return customerRepo.getById(this.customerId);
    }

    @Override
    public boolean login(String email, String password) throws LoginException {
       return customerRepo.existsCustomerByEmailAndPassword(email, password);
    }

    @Override
    public int getId() {
        return this.customerId;
    }
}
