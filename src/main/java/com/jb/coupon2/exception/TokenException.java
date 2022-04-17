package com.jb.coupon2.exception;

public class TokenException extends Exception{
    public TokenException() {
        super("General Token Exception");
    }

    public TokenException(String message) {
        super(message);
    }
}

