package com.jb.coupon2.advice;

import com.jb.coupon2.exception.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
public class LoginAdvice {
    @ExceptionHandler(value = {LoginException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail errorDetail(Exception err){
        return new ErrorDetail("Login Error: ", err.getMessage());
    }
}
