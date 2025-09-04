package com.cdcrane.stockflowbackend.authentication;

import com.cdcrane.stockflowbackend.authentication.dto.LoginDTO;
import com.cdcrane.stockflowbackend.authentication.dto.LoginResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {

        LoginResponseDTO response = authUseCase.login(loginDTO.username(), loginDTO.password());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + response.jwt());

        return ResponseEntity.ok().headers(headers).body(response);

    }

}
