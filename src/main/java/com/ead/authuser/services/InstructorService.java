package com.ead.authuser.services;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.models.User;

import java.util.UUID;

public interface InstructorService {

    UserDTO insertInstructor(UserDTO dto);

    UserDTO save (UserDTO dto);

    UserDTO updateUserToInstructor(UUID id);

    UserDTO updaterUserToInstructor(UUID id);

    UserDTO findById(UUID id);

    UserDTO updateInstructor(UUID id, UserDTO dto);

    UserDTO updaterInstructor(UUID id, UserDTO dto);

    UserDTO updateCpf(UUID id, UserDTO dto);

    UserDTO updaterCpf(UUID id, UserDTO dto);

    void updatePassword(UUID id, UserDTO dto);

    UserDTO updateImage(UUID id, UserDTO dto);

    void updaterImage(UUID id, UserDTO dto);

    void deleteById(UUID id);

    void deleterById(UUID id);
}
