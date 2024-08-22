package ru.skypro.homework.service.impl;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skypro.homework.dto.user.GetUserInfoDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserSetPasswordDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MyUserDetailsService userDetailsService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, MyUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userDetailsService = userDetailsService;
    }

    public void changeToPassword(UserSetPasswordDto userSetPasswordDto) {
       userDetailsService.changePassword(userSetPasswordDto.getCurrentPassword(),userSetPasswordDto.getNewPassword());

    }

    public GetUserInfoDto infoAboutUser(String name) {
       return userMapper.UserToGetUserInfo(userRepository.findByName(name));

    }

    public UpdateUserDto updateUser(UpdateUserDto updateUserDto,String email) {
        User user = userRepository.findByEmail(email);
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return userMapper.UserToUpdateUserDto(user);
    }

}
