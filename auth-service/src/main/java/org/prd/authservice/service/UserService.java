package org.prd.authservice.service;

import org.prd.authservice.model.dto.RegisterDto;
import org.prd.authservice.model.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void saveUser(RegisterDto registerDto);
    List<UserDto> findAll();
    UserDto findByUUID(UUID id);
}