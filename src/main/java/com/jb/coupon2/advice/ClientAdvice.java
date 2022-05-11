package com.jb.coupon2.advice;

import com.jb.coupon2.exception.AdminServiceException;
import com.jb.coupon2.exception.CompanyServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ClientAdvice {
    @ExceptionHandler(value = {AdminServiceException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail adminError(Exception err){
        return new ErrorDetail("admin error: ",err.getMessage());
    }

    @ExceptionHandler(value = {CompanyServiceException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail companyError(Exception err){
        return new ErrorDetail("Company Error: ", err.getMessage());
    }
}
