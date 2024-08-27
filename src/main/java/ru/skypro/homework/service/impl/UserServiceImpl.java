package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.GetUserInfoDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserSetPasswordDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Value("${path.to.image-avatar.folder}")
    private String imageDir;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MyUserDetailsService userDetailsService;
    private final AvatarRepository avatarRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, MyUserDetailsService userDetailsService, AvatarRepository avatarRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userDetailsService = userDetailsService;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void changeToPassword(UserSetPasswordDto userSetPasswordDto) {
        userDetailsService.changePassword(userSetPasswordDto.getCurrentPassword(), userSetPasswordDto.getNewPassword());

    }

    @Override
    public GetUserInfoDto infoAboutUser(String name) {
        return userMapper.UserToGetUserInfo(userRepository.findByEmail(name).orElseThrow());

    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return userMapper.UserToUpdateUserDto(user);
    }

    @Override
    public Avatar updateImage(Authentication authentication, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

            Path filePath = Path.of(imageDir, user.getId() + "." + getExtension(file.getOriginalFilename()));
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            try (
                    InputStream is = file.getInputStream();
                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
            ) {
                bis.transferTo(bos);
            }
            Avatar avatar = avatarRepository.findImageByUserId(user.getId()).orElseThrow();
            avatar.setUser(user);
            avatar.setFilePath(filePath.toString());
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(file.getBytes());
            user.setAvatar(avatar);
            userRepository.save(user);
            return avatar;
        }

    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
