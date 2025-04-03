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

    @Value("${iwt.expirationTime}")
    private int expirationTime;

    public String createJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("email", user.getEmailAddress())
                .setIssuer("TuanAnhDev2025")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
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
}
