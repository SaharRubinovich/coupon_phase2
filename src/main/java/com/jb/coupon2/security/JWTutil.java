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

    /**
     * generate token method
     * @param userDetails - instance that has the user relevant info to send in a token
     * @return - String of token with the Bearer start
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getId());
        claims.put("userType", userDetails.getUserType());
        return "Bearer " + createToken(claims, userDetails.getUserEmail());
    }

    /**
     * create token method
     * @param claims - map with the relevant info we want to put in the token.
     * @param userEmail - the subject of the token.
     * @return - string of finalized token.
     */
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

    /**
     * update the user token while logged in
     * @param token - get current used token
     * @return - new token after extracting the info from the last one
     */
    public String updateToken(String token){
        Claims claims = extractAllClaims(token.replace("Bearer ",""));
        UserDetails userDetails = new UserDetails();
        userDetails.setId((Integer) claims.get("userId"));
        userDetails.setUserType((String) claims.get("userType"));
        userDetails.setUserEmail(claims.getSubject());
        return generateToken(userDetails);
    }

    /**
     * get the signature method
     * @param token - the token we want to get the signature of.
     * @return - string of token signature.
     * @throws ExpiredJwtException - if the token is expired will throw exception
     * @throws MalformedJwtException - if the token is not right(have 2 . and etc) will reject and throw excrption.
     */
    private String extractSignature(String token) throws ExpiredJwtException, MalformedJwtException {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(decodedKey).build();
        return jwtParser.parseClaimsJws(token.replace("Bearer ", "")).getSignature();
    }

    /**
     * get subject method
     * @param token - the token we want the subject of.
     * @return - String of the token subject.
     */
    public String extractSubject(String token) {
        return extractAllClaims(token.replace("Bearer ", "")).getSubject();
    }

    /**
     * check if token expired
     * @param token - the token we want to check if still active.
     * @return - true if the token expired and false otherwise.
     */
    private boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.isEmpty();
    }

    /**
     * validate the token method
     * @param token - the token we want to check
     * @param email - the email of the user we want to verify if the token matches.
     * @return - true if the token matches the email.
     * @throws TokenException - if the token either doesn't match the email or is expired will throw custom exception
     * informing of validation error.
     */
    public boolean validateToken(String token, String email) throws TokenException {
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
                adminService.getId(), adminService.getUSER_EMAIL(), adminService.getUSER_PASS(),
                String.valueOf(UserType.ADMIN));
        JWTutil jwTutil = new JWTutil();
        String token = jwTutil.generateToken(userDetails);
        System.out.println(token);
        try {
            System.out.println(jwTutil.validateToken(token, adminService.getUSER_EMAIL()));
        } catch (TokenException e) {
            System.out.println(e.getMessage());
        }

        String newToken = jwTutil.updateToken(token);
        System.out.println(newToken);
        System.out.println(jwTutil.extractSubject(newToken));

         */
    }
}
