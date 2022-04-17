package com.jb.coupon2.exception;

public class LoginException extends Exception{
    public LoginException() {
        super("Error accord in login attempt.");
    }

    public LoginException(String message) {
        super(message);
    }
}
