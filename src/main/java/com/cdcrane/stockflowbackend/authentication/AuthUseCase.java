package com.cdcrane.stockflowbackend.authentication;

import com.cdcrane.stockflowbackend.authentication.dto.LoginResponseDTO;

public interface AuthUseCase {

    LoginResponseDTO login(String username, String password);
}
