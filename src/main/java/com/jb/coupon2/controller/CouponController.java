package com.jb.coupon2.controller;

import com.jb.coupon2.beans.Coupon;
import com.jb.coupon2.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/coupons")
public class CouponController {
    private final CouponService COUPON_SERVICE;

    @GetMapping("/allCoupons")
    public ResponseEntity<?> getAllCoupons(){
        return new ResponseEntity<>(COUPON_SERVICE.getAllCoupons(),HttpStatus.OK);
    }
}
