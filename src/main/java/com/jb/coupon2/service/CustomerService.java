package com.jb.coupon2.service;

import com.jb.coupon2.beans.Category;
import com.jb.coupon2.beans.Coupon;
import com.jb.coupon2.beans.Customer;
import com.jb.coupon2.exception.LoginException;
import com.jb.coupon2.exception.PurchaseException;
import com.jb.coupon2.repository.CustomerRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@Getter
public class CustomerService extends ClientService {
    private int customerId;

    /**
     * constructor for the service
     *
     * @param email    - customer email.
     * @param password - customer password.
     */
    public CustomerService(String email, String password) {
        this.customerId = customerRepo.findCustomerByEmailAndPassword(email, password).getId();
    }


    /**
     * purchase coupon method.
     *
     * @param coupon - the coupon we want to purchase.
     * @throws PurchaseException - will throw exception if the customer already purchased the coupon before,
     *                           if the coupon is out of stock or if the coupon is expired.
     */
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

    /**
     * get customer coupons list
     *
     * @return - list with customer coupons.
     */
    public List<Coupon> getCustomerCoupon() {
        return customerRepo.getById(this.customerId).getCoupons();
    }

    /**
     * get customer coupon from specific category
     *
     * @param category - the category we want to find coupons of
     * @return - filtered list of customer coupons that matches the asked category.
     */
    public List<Coupon> getCustomerCoupon(Category category) {
        List<Coupon> couponList = getCustomerCoupon();
        return couponList.stream().filter(
                        couponCategory -> category.ordinal() == couponCategory.getCategory().ordinal())
                .collect(Collectors.toList());
    }

    /**
     * get customer coupons up to specific price.
     *
     * @param maxPrice - the max price of coupons that we asked to see.
     * @return - filtered list of customer coupons up to the specific asked max price.
     */
    public List<Coupon> getCustomerCoupon(double maxPrice) {
        return getCustomerCoupon().stream().filter(
                coupon -> coupon.getPrice() <= maxPrice
        ).collect(Collectors.toList());
    }

    /**
     * get customer details.
     *
     * @return - customer instance of the current customer.
     */
    public Customer getCustomerDetails() {
        return customerRepo.getById(this.customerId);
    }

    /**
     * login method.
     *
     * @param email    - customer email.
     * @param password - customer password.
     * @return - true or false.
     */
    @Override
    public boolean login(String email, String password) {
        if (customerRepo.existsCustomerByEmailAndPassword(email, password)) {
            this.customerId = customerRepo.findCustomerByEmailAndPassword(email, password).getId();
            return true;
        }
        return false;
    }

    @Override
    public int getId() {
        return this.customerId;
    }
}
