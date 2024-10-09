package org.prd.authservice.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.prd.authservice.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;


    private Map<String, Object> generateDefaultExtraClaims(UserDetails user){
        Map<String, Object> extraClaims = new HashMap<>();

        String roleName = ( (User) user ).getRole().getRoleEnum().name();
        extraClaims.put("role",  roleName);
        extraClaims.put("message", "Expire in "+EXPIRATION_IN_MINUTES+"minutes");
        return extraClaims;
    }

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_IN_MINUTES * 60 * 1000));

        if(extraClaims == null){
            extraClaims = generateDefaultExtraClaims(user);
        }

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /*public String extractSubject(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }*/

    private Key generateKey() {
        byte[] keyAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyAsBytes);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(jwt).getBody();
    }

    /*public String resolve(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            return authHeader.replace("Bearer", "");
        }
        return null;
    }*/

    public String extractRole(String jwt){
        try{
            return (String) extractAllClaims(jwt).get("role");
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Invalid token");
        }
    }



   /* public boolean isTokenExpired(String role){
        return extractExpiration(role).before(new Date());
    }

    public Date extractExpiration(String role){
        return extractAllClaims(role).getExpiration();
    }

    public void expireToken(String role) {
        extractAllClaims(role).setExpiration(new Date());
    }*/
}
