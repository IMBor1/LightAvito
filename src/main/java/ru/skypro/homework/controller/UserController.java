package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.user.GetUserInfoDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UpdateUserImageDto;
import ru.skypro.homework.dto.user.UserSetPasswordDto;
import org.springframework.security.core.Authentication;
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
    @Operation(summary = "Обновление пароля")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<?> setPassword(@RequestBody UserSetPasswordDto userSetPasswordDto) {
        usersService.changeToPassword(userSetPasswordDto);
       return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<GetUserInfoDto> infoAboutUser(Authentication authentication) {
        GetUserInfoDto getUserInfoDto = usersService.infoAboutUser(authentication.name());
        return ResponseEntity.ok(getUserInfoDto);
    @Operation(summary = "Получение информации об авторизованном пользователе")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"
            )
    })
    public ResponseEntity getUser() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> updateUser(@RequestBody UpdateUserDto updateUserDto,Authentication authentication) {
UpdateUserDto updateUserDto1 = usersService.updateUser(updateUserDto, authentication.name());
            return ResponseEntity.ok(updateUserDto1);

    }

        @PatchMapping("/me/image")
        @Operation(summary = "Обновление аватара авторизованного пользователя")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "OK"),
                @ApiResponse(responseCode = "401", description = "Unauthorized")
        })
        public ResponseEntity updateImage (@RequestBody UpdateUserImageDto updateUserImageDto){
            return ResponseEntity.ok().build();
        }
    }

