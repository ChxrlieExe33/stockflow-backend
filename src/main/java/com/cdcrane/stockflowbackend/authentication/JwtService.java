package com.cdcrane.stockflowbackend.authentication;

import com.cdcrane.stockflowbackend.authentication.dto.JwtData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
    private SecretKey secretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration}")
    private long expirationTimeInMs;

    @PostConstruct
    private void initializeSecretKey() {

        if (secret == null || secret.isEmpty()) {
            throw new IllegalStateException("JWT secret must be set!");
        }

        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    }

    public JwtData createNewJwt(Authentication auth) {

        Date expiration = new Date(System.currentTimeMillis() + expirationTimeInMs);

        String jwt =  Jwts.builder()
                .issuer(issuer)
                .subject("JWT Token")
                .claim("username", auth.getName())
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(secretKey)
                .compact();

        return new JwtData(jwt, auth.getName(), expiration);

    }

    public Claims validateJwt(String jwt) {

        try {

            return Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(jwt)
                    .getPayload();

        } catch (ExpiredJwtException e) {

            throw new BadCredentialsException("JWT Token has expired: " + e.getMessage());

        } catch (Exception e) {

            throw new BadCredentialsException("Invalid JWT Token: ");
        }

    }

}
