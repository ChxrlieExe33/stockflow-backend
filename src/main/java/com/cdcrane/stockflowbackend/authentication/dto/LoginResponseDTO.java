package com.cdcrane.stockflowbackend.authentication.dto;

import java.util.Date;

public record LoginResponseDTO(String message, String username, Date expiration, String jwt) {
}
