package com.jb.coupon2.service;

import com.jb.coupon2.exception.LoginException;
import com.jb.coupon2.repository.CompanyRepo;
import com.jb.coupon2.repository.CouponRepo;
import com.jb.coupon2.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class ClientService {
    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CustomerRepo customerRepo;
    @Autowired
    protected CouponRepo couponRepo;
    protected int id;

    public abstract boolean login(String email, String password);

    public abstract int getId();
}
