package com.jb.coupon2.security;

import com.jb.coupon2.beans.UserDetails;
import com.jb.coupon2.beans.UserType;
import com.jb.coupon2.exception.TokenException;
import com.jb.coupon2.service.AdminService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTutil {
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    private String secretKey = "lets+do+weird+secret+key+that+will+fit+256+bit";
    private Key decodedKey = new SecretKeySpec(Base64.getDecoder().decode(secretKey), this.signatureAlgorithm);

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getId());
        claims.put("userType", userDetails.getUserType());
        return "Bearer " + createToken(claims, userDetails.getUserEmail());
    }

    private String createToken(Map<String, Object> claims, String userEmail) {
        Instant instant = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(instant.plus(5, ChronoUnit.MINUTES)))
                .signWith(decodedKey)
                .compact();
    }

    private Claims extractAllClaims(String token) throws ExpiredJwtException, MalformedJwtException {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(decodedKey).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    private String extractSignature(String token) throws ExpiredJwtException, MalformedJwtException {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(decodedKey).build();
        return jwtParser.parseClaimsJws(token.replace("Bearer ", "")).getSignature();
    }

    public String extractSubject(String token) {
        return extractAllClaims(token.replace("Bearer ", "")).getSubject();
    }

    private boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.isEmpty();

    }

    public boolean validateToken(String token, String email) throws TokenException {
        token = token.replace("Bearer ", "");
        String userEmail = extractSubject(token);
        if (userEmail.equals(email) && !isTokenExpired(token)) {
            return true;
        } else {
            throw new TokenException("Error validating token");
        }
    }

    public static void main(String[] args) {
        /*
        System.out.println("Token test\n ==============================");
        UserDetails userDetails = new UserDetails(3,"Sahar","12345",UserType.CUSTOMER);
        JWTutil jwTutil = new JWTutil();
        String token = jwTutil.generateToken(userDetails);
        System.out.println(token);
        System.out.println(jwTutil.extractSubject(token));
        System.out.println(jwTutil.extractSignature(token));
         */
        /*
        AdminService adminService = new AdminService();
        UserDetails userDetails = new UserDetails(
                adminService.getId(), adminService.getUSER_EMAIL(), adminService.getUSER_EMAIL(), UserType.ADMIN);
        JWTutil jwTutil = new JWTutil();
        String token = jwTutil.generateToken(userDetails);
        System.out.println(token);
        try {
            System.out.println(jwTutil.validateToken(token, adminService.getUSER_EMAIL()));
        } catch (TokenException e) {
            System.out.println(e.getMessage());
        }

         */
    }
}
