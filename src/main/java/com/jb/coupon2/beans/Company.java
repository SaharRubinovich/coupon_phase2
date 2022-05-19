package com.jb.coupon2.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jb.coupon2.exception.UnauthorizedException;
import com.jb.coupon2.security.JWTutil;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    @Singular
    private List<Coupon> coupons = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL , orphanRemoval = true)
    @JoinColumn(name = "token_id", referencedColumnName = "id")
    private TokenManager token;

    public void setId(int id) throws UnauthorizedException {
        throw new UnauthorizedException();
    }

    public void setName(String name) throws UnauthorizedException {
        throw new UnauthorizedException();
    }
}