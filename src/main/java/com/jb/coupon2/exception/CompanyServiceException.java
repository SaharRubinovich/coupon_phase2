package com.jb.coupon2.exception;

public class CompanyServiceException extends Exception{
    public CompanyServiceException() {
        super("General company service exception");
    }

    public CompanyServiceException(String message) {
        super(message);
    }
}
