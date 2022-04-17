package com.jb.coupon2.advice;

import com.jb.coupon2.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class UnauthorizedAdvice {
    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail unauthorizedError(Exception err){
        return new ErrorDetail("Error: ", err.getMessage());
    }
}
