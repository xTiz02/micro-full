package org.prd.authservice.web.controller;

import jakarta.validation.Valid;
import org.prd.authservice.model.dto.JWTResponse;
import org.prd.authservice.model.dto.LoginDto;
import org.prd.authservice.model.dto.RegisterDto;
import org.prd.authservice.model.dto.UriRequest;
import org.prd.authservice.service.UserService;
import org.prd.authservice.service.impl.AuthenticateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticateService authenticateService;
    private final UserService userService;

    public AuthController(AuthenticateService authenticateService, UserService userService) {
        this.authenticateService = authenticateService;
        this.userService = userService;
    }

    @GetMapping("/status/hellCheck")
    public ResponseEntity<String> hellCheck(){
        return ResponseEntity.ok("Hello from Auth Controller");
    }

    @PostMapping("/open/login")
    public ResponseEntity<JWTResponse> authenticate(
            @RequestBody @Valid LoginDto loginRequest
    ) {
        return ResponseEntity.ok(authenticateService.authenticate(loginRequest));
    }

    @PostMapping("/open/register")
    public ResponseEntity<String> authenticate(
            @RequestBody @Valid RegisterDto registerDto
    ) {
        userService.saveUser(registerDto);
        return ResponseEntity.ok("User ["+ registerDto.username()+"] registered successfully");
    }
    @PostMapping("/open/logout")
    public void logout() {
        authenticateService.logout();
    }

    @PostMapping("/open/validate")
    public ResponseEntity<JWTResponse> validate(@RequestParam String token,@Valid @RequestBody UriRequest dto){
        JWTResponse tokenDto = authenticateService.validate(token, dto);
        if(tokenDto == null) {
            return new ResponseEntity<>(new JWTResponse("Invalid token",token,"Not implemented"),HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(tokenDto);
    }
}