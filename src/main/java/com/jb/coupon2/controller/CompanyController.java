package com.jb.coupon2.controller;

import com.jb.coupon2.beans.Category;
import com.jb.coupon2.beans.Coupon;
import com.jb.coupon2.exception.CompanyServiceException;
import com.jb.coupon2.exception.TokenException;
import com.jb.coupon2.exception.UnauthorizedException;
import com.jb.coupon2.security.JWTutil;
import com.jb.coupon2.service.CompanyService;
import lombok.Getter;
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
    private String newToken;

    /**
     *
     * @param token - from the functions
     * @return - boolean
     * @throws TokenException - if the token has some error - will show a message.
     */
    private boolean checkToken(String token) throws TokenException, IllegalArgumentException {
        if (token == null || token.isEmpty()){
            throw new IllegalArgumentException();
        } else {
            return JWT.validateToken(token.replace("Bearer ", ""), COMPANY_SERVICE.getCompanyDetails().getEmail());
        }
    }

    /**
     *
     * @param token - from the front-end.
     * @param coupon - from the front-end.
     * @return - new token
     * @throws TokenException  - if the token has some error - will show a message.
     * @throws CompanyServiceException - if error accord while adding coupon
     * @throws UnauthorizedException - if didn't receive the right credentials
     */
    @PostMapping("/addCoupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon)
            throws TokenException, CompanyServiceException, UnauthorizedException {
        if (checkToken(token)) {
            COMPANY_SERVICE.addCoupon(coupon);
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(null);
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     *
     * @param token - from the front-end.
     * @param coupon - from the front-end.
     * @return - new token
     * @throws TokenException - if the token has some error - will show a message.
     * @throws CompanyServiceException - if error accord during updating
     * @throws UnauthorizedException - if didn't receive the right credentials
     */
    @PutMapping("/updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon)
            throws TokenException, CompanyServiceException, UnauthorizedException {
        if (checkToken(token)){
            COMPANY_SERVICE.updateCoupon(coupon);
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(null);
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     *
     * @param token - from the front-end.
     * @param id - from the front-end.
     * @return - new token
     * @throws TokenException - if the token has some error - will show a message.
     * @throws UnauthorizedException - if didn't receive the right credentials
     */
    @DeleteMapping("/deleteCoupon/{id}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization")String token, @PathVariable int id)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            COMPANY_SERVICE.deleteCoupon(id);
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(null);
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     *
     * @param token - from the front-end.
     * @return - list of all the current company coupons
     * @throws TokenException - if the token has some error - will show a message.
     * @throws UnauthorizedException - if didn't receive the right credentials
     */
    @GetMapping("/getAllCompanyCoupons")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization")String token)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(COMPANY_SERVICE.getCompanyCoupons());
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     *
     * @param token - from the front-end.
     * @param category - from the front-end.
     * @return - filtered list by category
     * @throws TokenException - if the token has some error - will show a message.
     * @throws UnauthorizedException - if didn't receive the right credentials
     */
    @GetMapping("/getCouponsByCategory")
    public ResponseEntity<?> getCouponsByCategory(@RequestHeader(name = "Authorization")String token, Category category)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(COMPANY_SERVICE.getCompanyCoupons(category));
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     *
     * @param token - from the front-end.
     * @param maxPrice - from the front-end.
     * @return - filtered list by price
     * @throws TokenException - if the token has some error - will show a message.
     * @throws UnauthorizedException - if didn't receive the right credentials
     */
    @GetMapping("/getCouponsByMaxPrice")
    public ResponseEntity<?> getCouponsByPrice(@RequestHeader(name = "Authorization")String token
            ,@RequestParam double maxPrice) throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(COMPANY_SERVICE.getCompanyCoupons(maxPrice));
        }else {
            throw new UnauthorizedException();
        }
    }

    /**
     *
     * @param token - from the front-end.
     * @return - company instance of current company
     * @throws TokenException - if the token has some error - will show a message.
     * @throws UnauthorizedException - if didn't receive the right credentials
     */
    @GetMapping("/getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization")String token)
            throws TokenException, UnauthorizedException {
        if (checkToken(token)){
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(COMPANY_SERVICE.getCompanyDetails());
        } else {
            throw new UnauthorizedException();
        }
    }
}
