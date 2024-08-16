package ru.skypro.homework.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String currentPassword;
    private String phone;
    private String image;
}

