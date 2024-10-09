package org.prd.apigateway.dto;

public record JwtResponse(
        String message,
        String token,
        String refreshToken
) {
}
