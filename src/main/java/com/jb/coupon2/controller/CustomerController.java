package com.jb.coupon2.controller;

import com.jb.coupon2.beans.Category;
import com.jb.coupon2.beans.Coupon;
import com.jb.coupon2.exception.PurchaseException;
import com.jb.coupon2.exception.TokenException;
import com.jb.coupon2.exception.UnauthorizedException;
import com.jb.coupon2.security.JWTutil;
import com.jb.coupon2.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "customer")
public class CustomerController {
    private final CustomerService CUSTOMER_SERVICE;
    private final JWTutil JWT;
    private String newToken;

    private boolean checkToken(String token) throws TokenException {
        return JWT.validateToken(token.replace("Bearer ", ""), CUSTOMER_SERVICE.getCustomerDetails().getEmail());
    }

    @PutMapping("/purchaseCoupon")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization")String token, @RequestBody Coupon coupon)
            throws TokenException, PurchaseException, UnauthorizedException {
        if (checkToken(token)){
            CUSTOMER_SERVICE.purchaseCoupon(coupon);
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(null);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization")String token)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(CUSTOMER_SERVICE.getCustomerCoupon());
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/getCustomerCouponsByCategory")
    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name = "Authorization")String token, Category category)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(CUSTOMER_SERVICE.getCustomerCoupon(category));
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/getCustomerCouponsByMoney/{price}")
    public ResponseEntity<?> getCustomerByMaxPrice(@RequestHeader(name = "Authorization")String token,@PathVariable double price)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(CUSTOMER_SERVICE.getCustomerCoupon(price));
        }else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization")String token)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("Authorization", newToken)
                    .body(CUSTOMER_SERVICE.getCustomerDetails());
        }else {
            throw new UnauthorizedException();
        }
    }
}
