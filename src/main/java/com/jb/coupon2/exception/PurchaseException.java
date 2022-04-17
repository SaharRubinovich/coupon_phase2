package com.jb.coupon2.exception;

public class PurchaseException extends Exception{
    public PurchaseException() {
        super("Error accord while trying to purchase coupon.");
    }

    public PurchaseException(String message) {
        super(message);
    }
}
