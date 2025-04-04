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

    public String createJwtToken(User user, boolean isRefreshToken) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer("TuanAnhDev2025")
                .setExpiration(new Date(System.currentTimeMillis() + (isRefreshToken ? refreshTokenExpirationTime : accessTokenExpirationTime)))
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
