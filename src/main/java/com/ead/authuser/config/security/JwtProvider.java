package com.ead.authuser.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtProvider {

    private Key key;

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;

    @Value("${ead.auth.jwtExpirationMs}")
    private int jwtExpirationsMs;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwt(Authentication authentication) {

        UserDetailsImpl userPrincipal =
                (UserDetailsImpl) authentication.getPrincipal();

        final String roles = userPrincipal.getAuthorities().stream()
                .map(role -> { return role.getAuthority();
                }).collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userPrincipal.getId().toString())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis() + jwtExpirationsMs
                ))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        return true;
        }catch(SignatureException e){
            log.error("Invalid JWT signature: {} ", e.getMessage());
        }catch(MalformedJwtException e){
            log.error("Invalid JWT token: {} ", e.getMessage());
        }catch(ExpiredJwtException e){
            log.error("JWT token is expired: {} ", e.getMessage());
        }catch(UnsupportedJwtException e){
            log.error("JWT token is unsupported: {} ", e.getMessage());
        }catch(IllegalArgumentException e){
            log.error("JWT claims string is empty: {} ", e.getMessage());
        }
        return false;
    }

    public String getSubjectJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

