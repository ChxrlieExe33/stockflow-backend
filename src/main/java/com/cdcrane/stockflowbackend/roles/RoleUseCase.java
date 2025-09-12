package com.cdcrane.stockflowbackend.roles;

public interface RoleUseCase {

    Role findByAuthority(String authority);

    Role getUserRole();

    Role getAdminRole();
}
