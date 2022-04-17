package com.jb.coupon2.beans;

import com.jb.coupon2.exception.UnauthorizedException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "coupons")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "company_id")
    private int companyId;
    @Column(name = "category_id")
    @Enumerated(EnumType.ORDINAL)
    private Category category;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    private String image;

    public void setId(int id) throws UnauthorizedException {
        throw new UnauthorizedException();
    }
}
