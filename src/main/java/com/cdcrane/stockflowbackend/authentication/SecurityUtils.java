package com.cdcrane.stockflowbackend.authentication;

import com.cdcrane.stockflowbackend.users.ApplicationUser;
import com.cdcrane.stockflowbackend.users.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtils {

    private final UserRepository userRepo;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    public ApplicationUser getCurrentAuth() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findByUsername(username);

    }
}
