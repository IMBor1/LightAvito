package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.GetUserInfoDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserSetPasswordDto;
import ru.skypro.homework.model.Avatar;

import java.io.IOException;

public interface UserService {
    public void changeToPassword(UserSetPasswordDto userSetPasswordDto);
    public GetUserInfoDto infoAboutUser(String name);
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto, String email);
    public Avatar updateImage(Authentication authentication, MultipartFile file)throws IOException;

}
