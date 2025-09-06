package com.cdcrane.stockflowbackend.config;

import com.cdcrane.stockflowbackend.authentication.JwtService;
import com.cdcrane.stockflowbackend.config.exception_handlers.CustomAccessDeniedHandler;
import com.cdcrane.stockflowbackend.config.exception_handlers.CustomAuthEntryPoint;
import com.cdcrane.stockflowbackend.config.filters.JwtValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
public class SecurityConfig {

    private final JwtService jwtService;
    private final CorsConfig corsConfig;

    public SecurityConfig(JwtService jwtService, CorsConfig corsConfig) {
        this.jwtService = jwtService;
        this.corsConfig = corsConfig;
    }

    public static final String[] PUBLIC_URIS = {
            "/error",
            "/api/v1/auth/login",
            "/api/v1/auth/register"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(PUBLIC_URIS).permitAll() // Permitted or specific routes first.
                .anyRequest().authenticated()); // .anyRequest always goes last.

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.cors(cors -> cors.configurationSource(corsConfig));

        http.addFilterAfter(new JwtValidatorFilter(jwtService), ExceptionTranslationFilter.class);

        http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomAuthEntryPoint()));
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();


    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
