package com.ead.authuser.tests;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class Factory {

    public static User createdUser(){

        User entity = new User(null, "TomásTechnology", "Tomás@gmail.com", "1234567", "Tomás Gonçalves", UserStatus.ACTIVE, UserType.STUDENT, "+55 11 99070-9070", "123-456-789-4",
                "www.image.com", LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")));

        return entity;
    }

    public static UserDTO createdUserDto() {

        UserDTO dto = new UserDTO(null, "MárioTechnology", "mario@gmail.com", "1234567", "Mario Gonçalves", "+55 11 2020-2720", "123.456.789-5", "www.image.com",
                UserStatus.ACTIVE, UserType.STUDENT, LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")));

        return dto;
    }

    public static UserDTO createdUserDtoToUpdate() {

        UserDTO dto = new UserDTO(null, "WilsonTechnology", "wilson@gmail.com", "1234567", "Wilson Gonçalves", "+55 11 1447-7441", "123.456.789-7", "www.image.com",
                UserStatus.ACTIVE, UserType.STUDENT, LocalDateTime.now(ZoneId.of("UTC")), LocalDateTime.now(ZoneId.of("UTC")));

        return dto;
    }

    public static UserDTO createdUserDtoToUpdatePassword() {

        UserDTO dto = new UserDTO("1234567", "1234567890", LocalDateTime.now(ZoneId.of("UTC")));

        return dto;
    }

    public static UserDTO createUserDtoToUpdateImage(){

        UserDTO dto = new UserDTO("www.img.com.br", LocalDateTime.now(ZoneId.of("UTC")));

        return dto;
    }
}
