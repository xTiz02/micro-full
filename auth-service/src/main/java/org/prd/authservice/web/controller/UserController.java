package org.prd.authservice.web.controller;

import org.prd.authservice.model.dto.UserDto;
import org.prd.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/status/hellCheck")
    public ResponseEntity<String> hellCheck(){
        return ResponseEntity.ok("Hello from User Controller");
    }

    @GetMapping("/restrict/all")
    public List<UserDto> findAll(){
        return userService.findAll();
    }

    @GetMapping("/restrict/id/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable(name = "id") UUID id){
        return ResponseEntity.ok(userService.findByUUID(id));
    }


}