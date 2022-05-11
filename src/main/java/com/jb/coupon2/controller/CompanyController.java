package com.jb.coupon2.controller;

import com.jb.coupon2.beans.Category;
import com.jb.coupon2.beans.Coupon;
import com.jb.coupon2.exception.CompanyServiceException;
import com.jb.coupon2.exception.TokenException;
import com.jb.coupon2.exception.UnauthorizedException;
import com.jb.coupon2.security.JWTutil;
import com.jb.coupon2.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "company")
public class CompanyController {
    private final CompanyService COMPANY_SERVICE;
    private final JWTutil JWT;

    private boolean checkToken(String token) throws TokenException {
        return JWT.validateToken(token.replace("Bearer ", ""), COMPANY_SERVICE.getCompanyDetails().getEmail());
    }

    @PostMapping("/addCoupon")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon)
            throws TokenException, CompanyServiceException, UnauthorizedException {
        if (checkToken(token)) {
            COMPANY_SERVICE.addCoupon(coupon);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PutMapping("/updateCoupon")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon)
            throws TokenException, CompanyServiceException, UnauthorizedException {
        if (checkToken(token)){
            COMPANY_SERVICE.updateCoupon(coupon);
        } else {
            throw new UnauthorizedException();
        }
    }

    @DeleteMapping("/deleteCoupon/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteCoupon(@RequestHeader(name = "Authorization")String token, @PathVariable int id)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            COMPANY_SERVICE.deleteCoupon(id);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/getAllCompanyCoupons")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization")String token)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            return new ResponseEntity<>(COMPANY_SERVICE.getCompanyCoupons(),HttpStatus.OK);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/getCouponsByCategory")
    public ResponseEntity<?> getCouponsByCategory(@RequestHeader(name = "Authorization")String token, Category category)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            return new ResponseEntity<>(COMPANY_SERVICE.getCompanyCoupons(category),HttpStatus.OK);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/getCouponsByMaxPrice")
    public ResponseEntity<?> getCouponsByPrice(@RequestHeader(name = "Authorization")String token
            ,@RequestParam double maxPrice) throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            return new ResponseEntity<>(COMPANY_SERVICE.getCompanyCoupons(maxPrice),HttpStatus.OK);
        }else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization")String token)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            return new ResponseEntity<>(COMPANY_SERVICE.getCompanyDetails(),HttpStatus.OK);
        } else {
            throw new UnauthorizedException();
        }
    }
}
