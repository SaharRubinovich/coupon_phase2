package com.jb.coupon2.repository;

import com.jb.coupon2.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;

@Repository
public interface CouponRepo extends JpaRepository<Coupon,Integer> {
    @Query(value = "SELECT COUNT(*) as counter FROM coupons_2.customers_vs_coupons " +
            "WHERE customer_id= :customer_Id and coupons_id= :coupon_Id", nativeQuery = true)
    int checkPurchase(int customer_Id, int coupon_Id);
    @Query(value = "SELECT COUNT(*) as counter FROM coupons_2.coupons \n" +
            "WHERE company_id= :company_Id and title= :title", nativeQuery = true)
    int checkIfCompanyHasSimilarCoupon(int company_Id, String title);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coupons_2.customers_vs_coupons " +
            "WHERE coupons_id= :coupon_Id", nativeQuery = true)
    void deleteCouponPurchasedHistory(int coupon_Id);
    @Transactional
    @Modifying
    void deleteCouponByCompanyId(int company_Id);
    @Query(value = "SELECT * FROM coupons_2.customers_vs_coupons " +
            "WHERE customer_id= :customer_Id", nativeQuery = true)
    void deleteCouponByCustomerId(int customer_Id);
    void deleteByEndDateBefore(Date date);
}
