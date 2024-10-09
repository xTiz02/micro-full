package org.prd.authservice.model.dto;

public record JWTResponse(
        String message,
        String token,
        String refreshToken
) {
}
