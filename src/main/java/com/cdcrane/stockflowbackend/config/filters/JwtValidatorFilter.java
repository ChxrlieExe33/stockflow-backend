package com.cdcrane.stockflowbackend.config.filters;

import com.cdcrane.stockflowbackend.authentication.JwtService;
import com.cdcrane.stockflowbackend.config.SecurityConfig;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtValidatorFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtValidatorFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");

        if (jwt != null && jwt.startsWith("Bearer ")) {

            String token = jwt.substring(7);

            Claims claims = jwtService.validateJwt(token);

            String username = claims.get("username", String.class);
            String authorities = claims.get("authorities", String.class);

            Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

            SecurityContextHolder.getContext().setAuthentication(auth);

        } else {

            throw new BadCredentialsException("Invalid JWT Token, must follow the 'Bearer <token>' format");
        }

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        for (String uri : SecurityConfig.PUBLIC_URIS){
            if (request.getRequestURI().equals(uri)){
                return true;
            }
        }

        return false;
    }
}
