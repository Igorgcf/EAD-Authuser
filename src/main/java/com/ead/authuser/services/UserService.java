package com.ead.authuser.services;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserService {

    Page<UserDTO> findAllPaged(Specification<User> spec, Pageable pageable);

    UserDTO findById(UUID id);

    UserDTO insert(UserDTO dto);

    UserDTO update(UUID id, UserDTO dto);

    UserDTO updateCpf (UUID id, UserDTO dto);

    void updatePassword(UUID id, UserDTO dto);

    UserDTO updateImage(UUID id, UserDTO dto);

    void deleteById(UUID id);
}
