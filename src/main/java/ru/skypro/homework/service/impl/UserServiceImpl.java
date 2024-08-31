package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.user.GetUserInfoDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserSetPasswordDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MyUserDetailsService userDetailsService;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, MyUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userDetailsService = userDetailsService;
    }
    @Override
    public void changeToPassword(UserSetPasswordDto userSetPasswordDto) {
       userDetailsService.changePassword(userSetPasswordDto.getCurrentPassword(),userSetPasswordDto.getNewPassword());

    }
    @Override
    public GetUserInfoDto infoAboutUser(String name) {
       return userMapper.UserToGetUserInfo(userRepository.findByEmail(name).orElseThrow());

    }
    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto,String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return userMapper.UserToUpdateUserDto(user);
    }
}
