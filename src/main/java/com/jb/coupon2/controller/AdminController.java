package com.jb.coupon2.controller;

import com.jb.coupon2.beans.Company;
import com.jb.coupon2.beans.Customer;
import com.jb.coupon2.exception.AdminServiceException;
import com.jb.coupon2.exception.TokenException;
import com.jb.coupon2.exception.UnauthorizedException;
import com.jb.coupon2.security.JWTutil;
import com.jb.coupon2.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "admin")
public class AdminController {
    private final AdminService ADMIN_SERVICE;
    private final JWTutil JWT;
    private String newToken;

    private boolean checkToken(String token) throws TokenException {
        return JWT.validateToken(token.replace("Bearer ",""), ADMIN_SERVICE.getUSER_EMAIL());
    }

    @PostMapping(value = "/addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company)
            throws AdminServiceException, UnauthorizedException, TokenException {
        if (checkToken(token)) {
            ADMIN_SERVICE.addCompany(company);
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(null);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PutMapping(value = "/updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company)
            throws UnauthorizedException, TokenException, AdminServiceException {
        if (checkToken(token)) {
            ADMIN_SERVICE.updateCompany(company);
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(null);
        } else {
            throw new UnauthorizedException();
        }
    }

    @DeleteMapping(value = "/deleteCompany")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @RequestParam int companyId)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)) {
            ADMIN_SERVICE.deleteCompany(companyId);
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(null);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)) {
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(ADMIN_SERVICE.getAllCompanies());
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getOneCompany")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @RequestParam int companyId)
            throws AdminServiceException, UnauthorizedException, TokenException {
        if (checkToken(token)) {
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(ADMIN_SERVICE.getOneCompany(companyId));
        } else {
            throw new UnauthorizedException();
        }
    }

    @PostMapping(value = "/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer)
            throws AdminServiceException, UnauthorizedException, TokenException {
        if (checkToken(token)){
            ADMIN_SERVICE.addCustomer(customer);
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(null);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PutMapping(value = "/updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token,@RequestBody Customer customer)
            throws UnauthorizedException, TokenException, AdminServiceException {
        if (checkToken(token)){
            ADMIN_SERVICE.updateCustomer(customer);
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(null);
        } else {
            throw new UnauthorizedException();
        }
    }

    @DeleteMapping(value = "/deleteCustomer")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token,@RequestParam int customerId)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)){
            ADMIN_SERVICE.deleteCustomer(customerId);
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(null);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getAllCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)){
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(ADMIN_SERVICE.getAllCustomers());
        }else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getCustomer")
    public ResponseEntity<?> getOneCustomer(
            @RequestHeader(name = "Authorization") String token,@RequestParam int customerId)
            throws UnauthorizedException, AdminServiceException, TokenException {
        if (checkToken(token)){
            newToken = JWT.checkUser(token);
            return ResponseEntity.ok()
                    .header("authorization", newToken)
                    .body(ADMIN_SERVICE.getOneCustomer(customerId));
        }else {
            throw new UnauthorizedException();
        }
    }
}