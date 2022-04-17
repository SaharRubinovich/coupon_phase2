package com.jb.coupon2.exception;

public class AdminServiceException extends Exception{
    public AdminServiceException() {
        super("General admin service exception.");
    }

    public AdminServiceException(String message) {
        super(message);
    }
}
