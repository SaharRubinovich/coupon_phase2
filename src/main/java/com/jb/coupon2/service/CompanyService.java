package com.jb.coupon2.service;

import com.jb.coupon2.beans.Category;
import com.jb.coupon2.beans.Company;
import com.jb.coupon2.beans.Coupon;
import com.jb.coupon2.exception.CompanyServiceException;
import com.jb.coupon2.exception.LoginException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyService extends ClientService{
    private int companyId;

    public CompanyService(String email, String password) {
        this.companyId = companyRepo.findCompanyByEmailAndPassword(email, password).getId();
    }

    public void addCoupon(Coupon coupon) throws CompanyServiceException{
        if (couponRepo.checkIfCompanyHasSimilarCoupon(this.companyId, coupon.getTitle()) == 0) {
            coupon.setCompanyId(this.companyId);
            couponRepo.save(coupon);
            System.out.println("Coupon was added.");
        } else {
            throw new CompanyServiceException("Error accord while trying to add coupon to company");
        }
    }
    public void updateCoupon(Coupon coupon) {
        try {
            couponRepo.saveAndFlush(coupon);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }
    public void deleteCoupon(int couponId) {
        couponRepo.deleteCouponPurchasedHistory(this.companyId);
    }
    public List<Coupon> getCompanyCoupons() {
        return companyRepo.getById(this.companyId).getCoupons();
    }
    public List<Coupon> getCompanyCoupons(Category category) {
        return companyRepo.getById(this.companyId).getCoupons().stream().filter(
                coupon -> category.ordinal() == coupon.getCategory().ordinal()
        ).collect(Collectors.toList());
    }
    public List<Coupon> getCompanyCoupons(double maxPrice) {
        return companyRepo.getById(this.companyId).getCoupons().stream().filter(
                coupon -> maxPrice>coupon.getPrice()
        ).collect(Collectors.toList());
    }
    public Company getCompanyDetails() {
        return companyRepo.getById(this.companyId);
    }

    @Override
    public boolean login(String email, String password) throws LoginException {
        return companyRepo.existsByEmailAndPassword(email, password);
    }

    @Override
    public int getId() {
        return this.companyId;
    }
}
