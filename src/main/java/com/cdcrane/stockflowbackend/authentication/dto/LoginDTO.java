package com.cdcrane.stockflowbackend.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank String username, @NotBlank String password) {
}
