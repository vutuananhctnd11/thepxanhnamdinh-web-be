package com.graduate.be_txnd_fanzone.util;

import com.graduate.be_txnd_fanzone.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.signKey}")
    private String signKey;

    @Value("${jwt.accessToken.expirationTime}")
    private int accessTokenExpirationTime;

    @Value("${jwt.refreshToken.expirationTime}")
    private int refreshTokenExpirationTime;

    public String createAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer("TuanAnhDev2025")
                .claim("role", "ROLE_" + user.getRole().getRoleName())
                .setExpiration(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (accessTokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS512, signKey)
                .compact();
    }

    public String createRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer("TuanAnhDev2025")
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .signWith(SignatureAlgorithm.HS512, signKey)
                .compact();
    }

    public String createForgotPasswordToken(String email, String otp) {
        return Jwts.builder()
                .setSubject(email)
                .claim("otp", otp)
                .claim("type", "OTP_RESET")
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .setIssuer("TuanAnhDev2025")
                .signWith(SignatureAlgorithm.HS512, signKey)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().equals(userDetails.getUsername()) && !claims.getExpiration().before(new Date());
    }

    public boolean isExpiredToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }
}
