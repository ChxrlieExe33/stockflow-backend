package com.cdcrane.stockflowbackend.users;

import com.cdcrane.stockflowbackend.roles.Role;
import com.cdcrane.stockflowbackend.roles.RoleUseCase;
import com.cdcrane.stockflowbackend.users.dto.RegisterUserDTO;
import com.cdcrane.stockflowbackend.users.exceptions.CannotCreateUserException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleUseCase roleUseCase;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleUseCase roleUseCase) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleUseCase = roleUseCase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser user = userRepo.findByUsernameWithRoles(username);

        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true,
                user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                    .collect(Collectors.toList()));

    }

    @Override
    @Transactional
    public void registerNewUser(RegisterUserDTO registerUserDTO) {

        if (userRepo.existsByUsername(registerUserDTO.username())) {

            throw new CannotCreateUserException("Username " + registerUserDTO.username() + " is already taken. Please choose a different username.");
        }

        String encodedPassword = passwordEncoder.encode(registerUserDTO.password());

        List<Role> roles = new ArrayList<>();
        Role userRole = roleUseCase.getUserRole();

        roles.add(userRole);

        if (registerUserDTO.isAdmin()) {

            Role adminRole = roleUseCase.getAdminRole();
            roles.add(adminRole);

        }

        ApplicationUser user = ApplicationUser.builder()
                .username(registerUserDTO.username())
                .firstName(registerUserDTO.firstName())
                .lastName(registerUserDTO.lastName())
                .password(encodedPassword)
                .roles(roles)
                .enabled(true)
                .build();

        userRepo.save(user);

    }
}
