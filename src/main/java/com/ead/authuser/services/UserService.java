package com.ead.authuser.services;

import com.ead.authuser.dto.JwtDTO;
import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.enums.PaymentControl;
import com.ead.authuser.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserService {

    Page<UserDTO> findAllPaged(Specification<User> spec, Pageable pageable);

    UserDTO findById(UUID id);

    UserDTO findByIdInternal(UUID id);

    UserDTO insert(UserDTO dto);

    UserDTO save(UserDTO dto);

    JwtDTO authentication (UserDTO dto);

    UserDTO insertAdmin(UserDTO dto);

    UserDTO update(UUID id, UserDTO dto);

    UserDTO updater(UUID id, UserDTO dto);

    UserDTO updateAfterPayment(UUID id, PaymentControl paymentControl);

    UserDTO updateCpf (UUID id, UserDTO dto);

    UserDTO updaterCpf (UUID id, UserDTO dto);

    void updatePassword(UUID id, UserDTO dto);

    UserDTO updateImage(UUID id, UserDTO dto);

    UserDTO updaterImage(UUID id, UserDTO dto);

    void deleteById(UUID id);

    void deleterById(UUID id);
}
