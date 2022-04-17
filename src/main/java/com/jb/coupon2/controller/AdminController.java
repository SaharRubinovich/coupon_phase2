package com.jb.coupon2.controller;

import com.jb.coupon2.beans.Company;
import com.jb.coupon2.beans.Customer;
import com.jb.coupon2.beans.UserDetails;
import com.jb.coupon2.beans.UserType;
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
    private final AdminService adminService;
    private JWTutil jwTutil;

    private boolean checkToken(String token) throws TokenException {
        return jwTutil.validateToken(token, adminService.getUSER_EMAIL());
    }

    @PostMapping(value = "/addCompany")
    @ResponseStatus(code = HttpStatus.OK)
    public void addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company)
            throws AdminServiceException, UnauthorizedException, TokenException {
        if (checkToken(token)) {
            adminService.addCompany(company);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PutMapping(value = "/updateCompany")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)) {
            adminService.updateCompany(company);
        } else {
            throw new UnauthorizedException();
        }
    }

    @DeleteMapping(value = "/deleteCompany")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteCompany(@RequestHeader(name = "Authorization") String token, @RequestParam int companyId)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)) {
            adminService.deleteCompany(companyId);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token)
            throws UnauthorizedException, TokenException {
        if (jwTutil.validateToken(token, adminService.getUSER_EMAIL())) {
            return new ResponseEntity<>(adminService.getAllCompanies(), HttpStatus.OK);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getOneCompany")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @RequestParam int companyId)
            throws AdminServiceException, UnauthorizedException, TokenException {
        if (checkToken(token)) {
            return new ResponseEntity<>(adminService.getOneCompany(companyId), HttpStatus.OK);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PostMapping(value = "/addCustomer")
    @ResponseStatus(code = HttpStatus.OK)
    public void addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer)
            throws AdminServiceException, UnauthorizedException, TokenException {
        if (checkToken(token)){
            adminService.addCustomer(customer);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PutMapping(value = "/updateCustomer")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateCustomer(@RequestHeader(name = "Authorization") String token,@RequestBody Customer customer)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)){
            adminService.updateCustomer(customer);
        } else {
            throw new UnauthorizedException();
        }
    }

    @DeleteMapping(value = "/deleteCustomer")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteCustomer(@RequestHeader(name = "Authorization") String token,@RequestParam int customerId)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)){
            adminService.deleteCustomer(customerId);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getAllCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token)
            throws UnauthorizedException, TokenException {
        if (checkToken(token)){
            return new ResponseEntity<>(adminService.getAllCustomers(),HttpStatus.OK);
        }else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping(value = "/getCustomer")
    public ResponseEntity<?> getOneCustomer(
            @RequestHeader(name = "Authorization") String token,@RequestParam int customerId)
            throws UnauthorizedException, AdminServiceException, TokenException {
        if (checkToken(token)){
            return new ResponseEntity<>(adminService.getOneCustomer(customerId),HttpStatus.OK);
        }else {
            throw new UnauthorizedException();
        }
    }
}