package com.cdcrane.stockflowbackend.authentication;

import com.cdcrane.stockflowbackend.authentication.dto.JwtData;
import com.cdcrane.stockflowbackend.authentication.dto.LoginResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService implements AuthUseCase{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponseDTO login(String username, String password) {

        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

        try {

            Authentication authentication = authenticationManager.authenticate(auth);

            JwtData data = jwtUtil.createNewJwt(authentication);

            log.info("User {} logged in successfully.", data.username());

            return new LoginResponseDTO("Authentication successful", data.username(), data.expiration(), data.jwt());

        } catch (AuthenticationException e) {

            log.warn("Authentication failed for user {}", username);

            throw new BadCredentialsException("Invalid username or password provided.");

        }

    }

}
