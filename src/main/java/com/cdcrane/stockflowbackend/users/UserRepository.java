package com.cdcrane.stockflowbackend.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, UUID> {

    ApplicationUser findByUsername(String username);

    @Query("SELECT u FROM ApplicationUser u JOIN FETCH u.roles")
    ApplicationUser findByUsernameWithRoles(String username);
}
