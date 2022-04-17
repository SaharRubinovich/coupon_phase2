package com.jb.coupon2.advice;

import com.jb.coupon2.exception.TokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class TokenAdvice {
    @ExceptionHandler(value = {ExpiredJwtException.class, MalformedJwtException.class, TokenException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail tokenError(Exception err){
        return new ErrorDetail("Token Error: ", err.getMessage());
    }
}
