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

    /**
     * daily coupon deletion.
     * each day at 6:00AM jerusalem time will preform a deletion of any coupon that is expired date came before
     * the day that the thread run in.
     */
    @Async
    @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Jerusalem")
    public void dailyCouponDelete(){
        System.out.println("Daily task running...");
        couponRepo.deleteByEndDateBefore(Date.valueOf(LocalDate.now()));
    }
}
