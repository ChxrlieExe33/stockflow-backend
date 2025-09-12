package com.cdcrane.stockflowbackend.users;

import com.cdcrane.stockflowbackend.users.dto.RegisterUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserUseCase extends UserDetailsService {

    void registerNewUser(RegisterUserDTO registerUserDTO);
}
