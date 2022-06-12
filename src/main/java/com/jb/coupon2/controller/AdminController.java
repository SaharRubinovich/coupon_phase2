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

    /**
     *
     * @param token
     * @return - boolean if the token is valid or not
     * @throws TokenException - if there is token error, will display message
     */
    private boolean checkToken(String token) throws TokenException, IllegalArgumentException {
        if (token == null || token.isEmpty()){
            throw new IllegalArgumentException();
        } else {
            return JWT.validateToken(token.replace("Bearer ", ""), ADMIN_SERVICE.getUSER_EMAIL());
        }
    }

    /**
     *
     * @param token - from the front-end
     * @param company - from the front-end
     * @return - new token to the front-end
     * @throws AdminServiceException - if there's a problem with the service logic
     * @throws UnauthorizedException - if the token doesn't have the right credentials
     * @throws TokenException - if the token is invalid for some reason
     */
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

    /**
     *
     * @param token - from the front-end
     * @param company - from the front-end
     * @return - new token to the front-end
     * @throws UnauthorizedException - if the token doesn't have the right credentials
     * @throws TokenException - if the token is invalid for some reason
     * @throws AdminServiceException - if there's a problem with the service logic
     */
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

    /**
     *
     * @param token - from the front-end
     * @param companyId - from the front-end
     * @return - a new JWT to the front-end
     * @throws UnauthorizedException - if the token doesn't have the right credentials
     * @throws TokenException - if the token is invalid for some reason
     */
    @DeleteMapping(value = "/deleteCompany")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @RequestParam int companyId)
            throws UnauthorizedException, TokenException, AdminServiceException {
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

    /**
     *
     * @param token - from the front-end
     * @return - list of all companies in the db.
     * @throws UnauthorizedException - if the token doesn't have the right credentials
     * @throws TokenException - if the token is invalid for some reason
     */
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

    /**
     *
     * @param token - from the front-end
     * @param companyId - from the front-end
     * @return - Single company instance that has the matching id
     * @throws AdminServiceException - if there's a problem with the service logic
     * @throws UnauthorizedException - if the token doesn't have the right credentials
     * @throws TokenException - if the token is invalid for some reason
     */
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

    /**
     *
     * @param token - from the front-end
     * @param customer - from the front-end
     * @return - a new token
     * @throws AdminServiceException - if there's a problem with the service logic
     * @throws UnauthorizedException - if the token doesn't have the right credentials
     * @throws TokenException - if the token is invalid for some reason
     */
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

    /**
     *
     * @param token - from the front-end
     * @param customer - from the front-end
     * @return - a new token
     * @throws UnauthorizedException - if the token doesn't have the right credentials
     * @throws TokenException - if the token is invalid for some reason
     * @throws AdminServiceException - if there's a problem with the service logic
     */
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

    /**
     *
     * @param token - from the front-end
     * @param customerId - from the front-end
     * @return - a new token
     * @throws UnauthorizedException - if the token doesn't have the right credentials
     * @throws TokenException - if the token is invalid for some reason
     */
    @DeleteMapping(value = "/deleteCustomer")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token,@RequestParam int customerId)
            throws UnauthorizedException, TokenException, AdminServiceException {
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

    /**
     *
     * @param token - from the front-end
     * @return - all customers in the db.
     * @throws UnauthorizedException - if the token doesn't have the right credentials
     * @throws TokenException - if the token is invalid for some reason
     */
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

    /**
     *
     * @param token - from the front-end
     * @param customerId - from the front-end
     * @return - single customer instance that matches the asked id
     * @throws UnauthorizedException - if the token doesn't have the right credentials
     * @throws AdminServiceException - if there's a problem with the service logic
     * @throws TokenException - if the token is invalid for some reason
     */
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