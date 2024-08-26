package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

    private final MyUserDetailsService manager;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UserServiceImpl userService;

    public AuthServiceImpl(MyUserDetailsService manager,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper,
                           UserServiceImpl userService) {
        this.manager = manager;
        this.encoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDto register) {
        if (manager.userExists(register.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(register.getPassword())
                        .username(register.getUsername())
                        .roles(register.getRole().name())
                        .build());
        userService.updateUser(userMapper.registerToUpdateUserDto(register), register.getUsername());
        return true;
    }

}
