package com.cdcrane.stockflowbackend.users;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser user = userRepo.findByUsernameWithRoles(username);

        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true,
                user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                    .collect(Collectors.toList()));

    }

}
