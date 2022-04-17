package com.jb.coupon2.beans;

import com.jb.coupon2.exception.UnauthorizedException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @JoinTable(name = "customers_vs_coupons")
    @Singular
    @ManyToMany
    List<Coupon> coupons = new ArrayList<>();


    public void setId(int id) throws UnauthorizedException {
        throw new UnauthorizedException();
    }
}
