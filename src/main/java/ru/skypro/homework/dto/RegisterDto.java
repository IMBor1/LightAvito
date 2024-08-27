package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.User;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class RegisterDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
