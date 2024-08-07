package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.users.GetUserInfoDto;
import ru.skypro.homework.dto.users.UpdateUserDto;
import ru.skypro.homework.dto.users.UpdateUserImageDto;
import ru.skypro.homework.dto.users.UserSetPasswordDto;
import ru.skypro.homework.service.UsersService;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("users")
public class UsersController {
   // private final UsersService usersService;

    @PostMapping("/set_password")
    public ResponseEntity setPassword(@RequestBody UserSetPasswordDto userSetPasswordDto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity getUser() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me")
    public ResponseEntity updateUser(@RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/me/image")
    public ResponseEntity updateImage(@RequestBody UpdateUserImageDto updateUserImageDto) {
        return ResponseEntity.ok().build();
    }


}
