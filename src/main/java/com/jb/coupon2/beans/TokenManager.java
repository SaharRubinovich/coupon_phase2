package com.jb.coupon2.beans;

import com.jb.coupon2.security.JWTutil;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class TokenManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String token;

    public TokenManager(UserDetails userDetails) {
        JWTutil jwTutil = new JWTutil();
        this.token = jwTutil.generateToken(userDetails);
    }
}
