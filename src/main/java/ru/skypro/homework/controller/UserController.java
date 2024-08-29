package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.GetUserInfoDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserSetPasswordDto;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.service.impl.AvatarServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.io.IOException;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
     private final UserServiceImpl usersService;
    private final AvatarServiceImpl avatarService;
    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public void setPassword(@RequestBody UserSetPasswordDto userSetPasswordDto) {
        usersService.changeToPassword(userSetPasswordDto);
    }
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
    @GetMapping("/me")
    public ResponseEntity<GetUserInfoDto> infoAboutUser(Authentication authentication) {
        GetUserInfoDto getUserInfoDto = usersService.infoAboutUser(authentication.getName());
        return ResponseEntity.ok(getUserInfoDto);
    }

        @PatchMapping("/me")
        public ResponseEntity<UpdateUserDto> updateUser (@RequestBody UpdateUserDto updateUserDto, Authentication authentication){
            UpdateUserDto updateUserDto1 = usersService.updateUser(updateUserDto, authentication.getName());
            return ResponseEntity.ok(updateUserDto1);

        }

        @PatchMapping("/me/image")
        @Operation(summary = "Обновление аватара авторизованного пользователя")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "OK"),
                @ApiResponse(responseCode = "401", description = "Unauthorized")
        })
        public void updateImage (Authentication authentication, @RequestPart MultipartFile image) throws IOException{
            avatarService.updateImage(authentication, image);
        }



    }

