package org.prd.authservice.service.impl;

import org.prd.authservice.model.dto.RegisterDto;
import org.prd.authservice.model.dto.UserDto;
import org.prd.authservice.model.entity.User;
import org.prd.authservice.model.repository.RoleRepository;
import org.prd.authservice.model.repository.UserRepository;
import org.prd.authservice.service.UserService;
import org.prd.authservice.util.RoleEnum;
import org.prd.authservice.util.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void saveUser(RegisterDto registerDto) {
        User user = UserMapper.registerDtoToUser(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setRole(roleRepository.findByRoleEnum(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found")));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::userToUserDto)
                .toList();
    }

}
