package com.ead.authuser.services;

import com.ead.authuser.dto.UserDTO;

import java.util.UUID;

public interface InstructorService {

    UserDTO insertInstructor(UserDTO dto);

    void updateUserToInstructor(UUID id);

    UserDTO findById(UUID id);

    UserDTO updateInstructor(UUID id, UserDTO dto);

    UserDTO updateCpf(UUID id, UserDTO dto);

    void updatePassword(UUID id, UserDTO dto);

    void updateImage(UUID id, UserDTO dto);

    void deleteById(UUID id);
}
