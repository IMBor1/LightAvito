package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.user.GetUserInfoDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UpdateUserImageDto;
import ru.skypro.homework.dto.user.UserSetPasswordDto;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.impl.UserServiceImpl;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
     private final UserServiceImpl usersService;

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody UserSetPasswordDto userSetPasswordDto) {
        usersService.changeToPassword(userSetPasswordDto);
       return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<GetUserInfoDto> infoAboutUser(Authentication authentication) {
        GetUserInfoDto getUserInfoDto = usersService.infoAboutUser(authentication.name());
        return ResponseEntity.ok(getUserInfoDto);
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> updateUser(@RequestBody UpdateUserDto updateUserDto,Authentication authentication) {
UpdateUserDto updateUserDto1 = usersService.updateUser(updateUserDto, authentication.name());
            return ResponseEntity.ok(updateUserDto1);

    }

    @PatchMapping("/me/image")
    public ResponseEntity updateImage(@RequestBody UpdateUserImageDto updateUserImageDto) {
        return ResponseEntity.ok().build();
    }


}
