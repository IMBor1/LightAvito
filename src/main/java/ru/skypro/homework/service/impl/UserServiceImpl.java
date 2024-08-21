package ru.skypro.homework.service.impl;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.user.GetUserInfoDto;
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

    public void changeToPassword(UserSetPasswordDto userSetPasswordDto,String name) {
       userDetailsService.changePassword(userSetPasswordDto.getCurrentPassword(),userSetPasswordDto.getNewPassword());

    }

    public User infoAboutUser(GetUserInfoDto getUserInfoDto) {
        User user = userMapper.GetUserInfoToUser(getUserInfoDto);
        return userRepository.save(user);
    }

}
