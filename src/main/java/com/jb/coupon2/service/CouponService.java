package com.jb.coupon2.service;

import com.jb.coupon2.beans.Coupon;
import com.jb.coupon2.repository.CouponRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CouponService {
    @Autowired
    private CouponRepo couponRepo;

    public List<Coupon> getAllCoupons(){
        return couponRepo.findAll();
    }
}
