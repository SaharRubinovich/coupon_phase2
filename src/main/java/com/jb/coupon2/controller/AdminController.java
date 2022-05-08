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

    private boolean checkToken(String token) throws TokenException {
        return JWT.validateToken(token.replace("Bearer ",""), ADMIN_SERVICE.getUSER_EMAIL());
    }

    @PostMapping(value = "/addCompany")
    @ResponseStatus(code = HttpStatus.OK)
    public void addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company)
            throws AdminServiceException, UnauthorizedException, TokenException {
        if (checkToken(token)) {
            ADMIN_SERVICE.addCompany(company);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PutMapping(value = "/updateCompany")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company)
            throws UnauthorizedException, TokenException, AdminServiceException {
        if (checkToken(token)) {
            ADMIN_SERVICE.updateCompany(company);
        } else {
            throw new UnauthorizedException();
        }
    }

    @DeleteMapping(value = "/deleteCompany")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteCompany(@RequestHeader(name = "Authorization") String token, @RequestParam int companyId)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)) {
            ADMIN_SERVICE.deleteCompany(companyId);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)) {
            return new ResponseEntity<>(ADMIN_SERVICE.getAllCompanies(), HttpStatus.OK);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getOneCompany")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @RequestParam int companyId)
            throws AdminServiceException, UnauthorizedException, TokenException {
        if (checkToken(token)) {
            return new ResponseEntity<>(ADMIN_SERVICE.getOneCompany(companyId), HttpStatus.OK);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PostMapping(value = "/addCustomer")
    @ResponseStatus(code = HttpStatus.OK)
    public void addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer)
            throws AdminServiceException, UnauthorizedException, TokenException {
        if (checkToken(token)){
            ADMIN_SERVICE.addCustomer(customer);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PutMapping(value = "/updateCustomer")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateCustomer(@RequestHeader(name = "Authorization") String token,@RequestBody Customer customer)
            throws UnauthorizedException, TokenException, AdminServiceException {
        if (checkToken(token)){
            ADMIN_SERVICE.updateCustomer(customer);
        } else {
            throw new UnauthorizedException();
        }
    }

    @DeleteMapping(value = "/deleteCustomer")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteCustomer(@RequestHeader(name = "Authorization") String token,@RequestParam int customerId)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)){
            ADMIN_SERVICE.deleteCustomer(customerId);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getAllCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)){
            return new ResponseEntity<>(ADMIN_SERVICE.getAllCustomers(),HttpStatus.OK);
        }else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getCustomer")
    public ResponseEntity<?> getOneCustomer(
            @RequestHeader(name = "Authorization") String token,@RequestParam int customerId)
            throws UnauthorizedException, AdminServiceException, TokenException {
        if (checkToken(token)){
            return new ResponseEntity<>(ADMIN_SERVICE.getOneCustomer(customerId),HttpStatus.OK);
        }else {
            throw new UnauthorizedException();
        }
    }
}