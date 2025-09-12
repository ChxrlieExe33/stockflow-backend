package com.cdcrane.stockflowbackend.users.dto;

public record RegisterUserDTO(String username, String firstName, String lastName, String password, boolean isAdmin) {
}
