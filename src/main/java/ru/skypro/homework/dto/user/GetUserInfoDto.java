package ru.skypro.homework.dto.user;

import lombok.Data;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.User;

@Data
public class GetUserInfoDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;

}
