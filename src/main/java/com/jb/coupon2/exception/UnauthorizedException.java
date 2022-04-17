package com.jb.coupon2.exception;

public class UnauthorizedException extends Exception{
    public UnauthorizedException() {
        super("User Cannot preform this action.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
