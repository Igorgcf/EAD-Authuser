package com.ead.authuser.services.impl;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import com.ead.authuser.services.exceptions.BadRequestException;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Specification<User> spec, Pageable pageable) {
        Page<User> page = repository.findAll(spec, pageable);
        return page.map(UserDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(UUID id) {
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId()));
        return new UserDTO(entity);
    }

    @Override
    @Transactional
    public UserDTO insert(UserDTO dto) {

        log.debug("Insert UserDTO received {} ", dto.toString());

        User entity = new User();
        entity.setUserType(UserType.STUDENT);
        entity.setUserStatus(UserStatus.ACTIVE);
        entity.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        copyDtoToEntity(entity, dto);
        repository.save(entity);

        log.debug("Insert User saved {} ", entity.toString());
        log.info("User Saved successfully Id: {}", entity.getId());

        return new UserDTO(entity);
    }

    @Override
    @Transactional
    public UserDTO update(UUID id, UserDTO dto) {

        log.debug("Update UserDTO received {} ", dto.toString());

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        entity.setFullName(dto.getFullName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setCpf(dto.getCpf());
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);

        log.debug("Update User saved {} ", entity.toString());
        log.info("User updated successfully Id: {}", entity.getId());

        return new UserDTO(entity);

    }

    @Override
    @Transactional
    public void updatePassword(UUID id, UserDTO dto) {

        log.debug("UpdatePassword oldPassword received {} ", dto.getOldPassword());

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        if (!entity.getPassword().equals(dto.getOldPassword())) {
            log.warn("Error: Mismatched old password UserId {}.", entity.getId());
            throw new BadRequestException("Error: Mismatched old password.");
        } else {
            entity.setPassword(dto.getPassword());
            entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            repository.save(entity);

            log.debug("UpdatePassword password saved {}", entity.getPassword());
            log.info("Password updated successfully Id: {}", entity.getId());
        }
    }

    @Override
    @Transactional
    public UserDTO updateImage(UUID id, UserDTO dto){

        log.debug("UpdateImage imageUrl received {} ", dto.getImageUrl());

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        entity.setImageUrl(dto.getImageUrl());
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        repository.save(entity);

        log.debug("UpdateImage imageUrl saved {} ", entity.getImageUrl());
        log.info("ImageUrl updated successfully imageUrl {}", entity.getImageUrl());

        return new UserDTO(entity);

    }

    @Override
    public void deleteById(UUID id) {

        log.debug("DeleteById id received: {}", id);

        Optional<User> obj = repository.findById(id);
        if(obj.isEmpty()){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
        repository.deleteById(id);
        log.debug("User deleted successfully Id: {}", id);
        log.info("User deleted successfully Id: {}", id);
    }

    void copyDtoToEntity(User entity, UserDTO dto){

        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setFullName(dto.getFullName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setCpf(dto.getCpf());
        entity.setImageUrl(dto.getImageUrl());
    }
}