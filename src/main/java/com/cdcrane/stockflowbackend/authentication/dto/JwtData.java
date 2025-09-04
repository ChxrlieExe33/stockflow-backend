package com.cdcrane.stockflowbackend.authentication.dto;

import java.util.Date;

public record JwtData(String jwt, String username, Date expiration) {
}
