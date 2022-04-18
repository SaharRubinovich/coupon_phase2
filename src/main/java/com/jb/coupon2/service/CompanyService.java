package com.jb.coupon2.service;

import com.jb.coupon2.beans.Category;
import com.jb.coupon2.beans.Company;
import com.jb.coupon2.beans.Coupon;
import com.jb.coupon2.exception.CompanyServiceException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyService extends ClientService {
    private int companyId;

    /**
     * constructor for the service.
     *
     * @param email    - the company email
     * @param password - the company password
     *                 will update the companyId instance after the login happens.
     */
    public CompanyService(String email, String password) {
      this.companyId = companyRepo.findCompanyByEmailAndPassword(email, password).getId();
    }

    /**
     * add coupon to company method.
     *
     * @param coupon - coupon instance we want to add.
     * @throws CompanyServiceException - if the coupon we want to add exist already or the company have coupon with
     *                                 the same title will throw exception.
     */
    public void addCoupon(Coupon coupon) throws CompanyServiceException {
        if (couponRepo.checkIfCompanyHasSimilarCoupon(this.companyId, coupon.getTitle()) == 0) {
            coupon.setCompanyId(this.companyId);
            couponRepo.save(coupon);
            /*
            Company company = companyRepo.getById(companyId);
            List<Coupon> couponList = company.getCoupons();
            couponList.add(coupon);
            company.setCoupons(couponList);
            companyRepo.saveAndFlush(company);
             */
            System.out.println("Coupon was added.");
        } else {
            throw new CompanyServiceException("Error accord while trying to add coupon to company");
        }
    }

    /**
     * update coupon method.
     *
     * @param coupon - updated coupon instance.
     * @throws CompanyServiceException - if coupon wasn't found or there was an attempt to change the company id or
     *                                 the general id will throw exception to inform the user.
     */
    public void updateCoupon(Coupon coupon) throws CompanyServiceException {
        if (couponRepo.findById(companyId).isEmpty()) {
            throw new CompanyServiceException("Coupon wasn't found in the db.");
        }
        if (couponRepo.getById(coupon.getId()).getCompanyId() != coupon.getCompanyId() ||
                couponRepo.getById(coupon.getId()).getId() != coupon.getId()) {
            throw new CompanyServiceException("Cannot change coupon Id, or coupon company Id");
        }
        couponRepo.saveAndFlush(coupon);
        System.out.println("Coupon was updated!");
    }

    /**
     * delete coupon method.
     *
     * @param couponId - integer of the coupon id we want to delete.
     */
    public void deleteCoupon(int couponId) {
        couponRepo.deleteCouponPurchasedHistory(this.companyId);
        couponRepo.deleteById(couponId);
        System.out.println("Coupon was deleted.");
    }

    /**
     * get company coupons list method.
     *
     * @return - list of all the company coupons.
     */
    public List<Coupon> getCompanyCoupons() {
        return companyRepo.getById(this.companyId).getCoupons();
    }

    /**
     * get company coupons from specific category
     *
     * @param category - the category we want to find coupons of.
     * @return - filtered list of the company coupons from the category that was asked.
     */
    public List<Coupon> getCompanyCoupons(Category category) {
        return companyRepo.getById(this.companyId).getCoupons().stream().filter(
                coupon -> category.ordinal() == coupon.getCategory().ordinal()
        ).collect(Collectors.toList());
    }

    /**
     * get company coupons from specific price range
     *
     * @param maxPrice - the max price of the coupon that will be on the list.
     * @return - filtered list of company coupons up to the price of the asked max price.
     */
    public List<Coupon> getCompanyCoupons(double maxPrice) {
        return companyRepo.getById(this.companyId).getCoupons().stream().filter(
                coupon -> maxPrice > coupon.getPrice()
        ).collect(Collectors.toList());
    }

    /**
     * get company instance.
     *
     * @return - company instance of the current company.
     */
    public Company getCompanyDetails() {
        return companyRepo.getById(this.companyId);
    }

    /**
     * login method.
     *
     * @param email    - company email.
     * @param password - company password.
     * @return - true or false.
     */
    @Override
    public boolean login(String email, String password) {
        if (companyRepo.existsByEmailAndPassword(email, password)){
            this.companyId = companyRepo.findCompanyByEmailAndPassword(email, password).getId();
            return true;
        }
        return false;
    }

    @Override
    public int getId() {
        return this.companyId;
    }
}
