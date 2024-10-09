package org.prd.authservice.service.impl;

import org.prd.authservice.model.dto.JWTResponse;
import org.prd.authservice.model.dto.LoginDto;
import org.prd.authservice.model.dto.UriRequest;
import org.prd.authservice.util.RouteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {
    @Autowired
    private HttpSecurity http;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RouteValidator routeValidator;

    public JWTResponse authenticate(LoginDto loginRequest) {
        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.username());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, loginRequest.password(), user.getAuthorities()
        );
        authenticationManager.authenticate(authentication);

        String jwt = jwtService.generateToken(user, null);

        return new JWTResponse("Token Valid",jwt,"Not implemented");
    }


    public void logout() {
        try{
            http.logout(logoutConfig -> {
                logoutConfig.deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true);
            });
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }

    public JWTResponse validate(String token, UriRequest dto) {
        try{
            if(routeValidator.isPathValid(token,dto)){
                return new JWTResponse("Token Valid",token,"Not implemented");
            }
            return null;
        }catch (Exception exception){
            return null;
        }
    }

    /*public UserDetails getUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( !(authentication instanceof UsernamePasswordAuthenticationToken) ){
            throw new AuthenticationCredentialsNotFoundException("Se requiere autenticación completa");
        }

        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        return (UserDetails) authToken.getPrincipal();
    }*/
}
