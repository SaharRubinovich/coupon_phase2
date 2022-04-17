package com.jb.coupon2.jobs;

import com.jb.coupon2.repository.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@EnableAsync
@EnableScheduling
public class CouponExpirationDailyJob {
    @Autowired
    private CouponRepo couponRepo;

    @Async
    @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Jerusalem")
    public void dailyCouponDelete(){
        System.out.println("Daily task running...");
        couponRepo.deleteByEndDateBefore(Date.valueOf(LocalDate.now()));
    }
}
