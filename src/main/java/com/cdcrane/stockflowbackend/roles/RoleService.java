package com.cdcrane.stockflowbackend.roles;

import com.cdcrane.stockflowbackend.roles.exceptions.RoleDoesntExistException;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleUseCase{

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByAuthority(String authority) {

        return roleRepository.findByAuthority(authority).orElseThrow(() -> new RoleDoesntExistException("Role with authority " + authority + " doesn't exist. Please contact the developer for assistance."));

    }

    @Override
    public Role getUserRole() {

        return roleRepository.findByAuthority("USER").orElseThrow(() -> new RoleDoesntExistException("User role doesn't exist. Please contact the developer for assistance."));

    }

    @Override
    public Role getAdminRole() {

        return roleRepository.findByAuthority("ADMIN").orElseThrow(() -> new RoleDoesntExistException("Admin role doesn't exist. Please contact the developer for assistance."));

    }


}
