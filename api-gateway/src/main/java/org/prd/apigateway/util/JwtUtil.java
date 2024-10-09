package org.prd.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    public String resolve(String authHeader) {
        String [] chunks = authHeader.split(" ");
        if (authHeader != null && authHeader.startsWith("Bearer") && chunks.length == 2) {
            return authHeader.replace("Bearer", "");
        }
        return null;
    }

}
