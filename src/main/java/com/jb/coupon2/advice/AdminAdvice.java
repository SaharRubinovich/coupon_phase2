package com.jb.coupon2.advice;

import com.jb.coupon2.exception.AdminServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class AdminAdvice {
    @ExceptionHandler(value = {AdminServiceException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail adminError(Exception err){
        return new ErrorDetail("admin error: ",err.getMessage());
    }
}
