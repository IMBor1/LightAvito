package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> setPassword(@RequestBody UserSetPasswordDto userSetPasswordDto,
                                         Authentication authentication) {
        usersService.changeToPassword(userSetPasswordDto,authentication.name());
       return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity getUser() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me")
    public ResponseEntity updateUser(@RequestBody UpdateUserDto updateUserDto) {
        if (updateUserDto != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/me/image")
    public ResponseEntity updateImage(@RequestBody UpdateUserImageDto updateUserImageDto) {
        return ResponseEntity.ok().build();
    }


}
