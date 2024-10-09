package org.prd.authservice.service;

import org.prd.authservice.model.dto.RegisterDto;
import org.prd.authservice.model.dto.UserDto;

import java.util.List;

public interface UserService {

    void saveUser(RegisterDto registerDto);
    List<UserDto> findAll();
}
