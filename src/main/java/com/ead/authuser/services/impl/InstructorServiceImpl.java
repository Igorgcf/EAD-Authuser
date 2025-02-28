package com.ead.authuser.services.impl;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.InstructorService;
import com.ead.authuser.services.exceptions.BadRequestException;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public UserDTO insertInstructor(UserDTO dto) {

        log.debug("Insert Instructor UserDTO received: {} ", dto.toString());

        User entity = new User();

        copyDtoToEntity(entity, dto);;
        entity.setUserType(UserType.INSTRUCTOR);
        entity.setUserStatus(UserStatus.ACTIVE);
        entity.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        repository.save(entity);

        log.debug("Insert Instructor User Instructor Saved: {} ", entity.toString());
        log.info("User Instructor Saved Successfully Id: {} ", entity.getId());

        return new UserDTO(entity);
    }

    @Transactional
    @Override
    public void updateUserToInstructor(UUID id) {

        log.debug("Update User to Instructor Id received: {} ", id);

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

        entity.setUserType(UserType.INSTRUCTOR);
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        repository.save(entity);

        log.debug("Update User to Instructor User Updated: {} ", entity.toString());
        log.info("User Updated to Instructor Successfully Id: {} ", entity.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO findById(UUID id) {

        log.debug("FindById Id received: {} ", id);

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

        if(!entity.getUserType().equals(UserType.INSTRUCTOR)) {
            throw new BadRequestException("Error: User is not an instructor.");
        }
        log.debug("FindById User Found: {} ", entity.toString());
        log.info("User Found Successfully Id: {} ", entity.getId());

        return new UserDTO(entity);
    }

    @Transactional
    @Override
    public UserDTO updateInstructor(UUID id, UserDTO dto) {

        log.debug("Update Instructor UserDTO received: {} ", dto.toString());

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

        if(!entity.getUserType().equals(UserType.INSTRUCTOR)){
            throw new BadRequestException("Error: User is not an instructor.");
        }

        copyDtoToEntity(entity, dto);
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        repository.save(entity);

        log.debug("Update Instructor User Instructor Updated: {} ", entity.toString());
        log.info("User Instructor Update Successfully Id: {} ", entity.getId());

        return new UserDTO(entity);
    }

    @Transactional
    @Override
    public UserDTO updateCpf(UUID id, UserDTO dto) {

        log.debug("Update CPF UserDTO received: {} ", dto.getCpf());

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

        if(!entity.getUserType().equals(UserType.INSTRUCTOR)){
            throw new BadRequestException("Error: User is not an instructor.");
        }

        copyDtoToEntity(entity, dto);
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        repository.save(entity);

        log.debug("Update CPF CPF Saved: {} ", entity.getCpf());
        log.info("CPF updated Successfully Id: {} ", entity.getId());
        return new UserDTO(entity);
    }

    @Transactional
    @Override
    public void updatePassword(UUID id, UserDTO dto) {

        log.debug("Update Password oldPassword received: {} ", dto.getOldPassword());

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

        if(!entity.getUserType().equals(UserType.INSTRUCTOR)){
            throw new BadRequestException("Error: User is not an instructor.");
        }

        if(!dto.getOldPassword().equals(entity.getPassword())){
            throw new BadRequestException("Error: Mismatched old password.");
        }

        copyDtoToEntity(entity, dto);
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        repository.save(entity);

        log.debug("Update Password password saved: {} ", entity.getPassword());
        log.info("Password updated successfully Id: {} ", entity.getId());
    }

    @Transactional
    @Override
    public void updateImage(UUID id, UserDTO dto) {

        log.debug("Update Image ImageUrl Received: {} ", dto.getImageUrl());

        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

        if(!entity.getUserType().equals(UserType.INSTRUCTOR)){
            throw new BadRequestException("Error: User is not an instructor.");
        }

        copyDtoToEntity(entity, dto);
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        repository.save(entity);

        log.debug("Update Image ImageUrl Saved: {} ", entity.getImageUrl());
        log.info("ImageUrl Update Successfully Id: {} ", entity.getId());
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {

        log.debug("DeleteById Id received: {} ", id);

        Optional<User> obj = repository.findById(id);
        if(obj.isEmpty()){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
        repository.deleteById(id);

        log.debug("DeleteById User Deleted Id: {} ", id);
        log.info("User Deleted Successfully Id: {} ", id);
    }

    void copyDtoToEntity(User entity, UserDTO dto){

            if(dto.getUsername() != null){
                entity.setUsername(dto.getUsername());
            }else{
                entity.setUsername(entity.getUsername());
            }

            if(dto.getEmail() != null){
                entity.setEmail(dto.getEmail());
            }else{
                entity.setEmail(entity.getEmail());
            }

            if(dto.getPassword() != null) {
                entity.setPassword(dto.getPassword());
            }else{
                entity.setPassword(entity.getPassword());
            }

            if(dto.getFullName() != null) {
                entity.setFullName(dto.getFullName());
            }else{
                entity.setFullName(entity.getFullName());
            }

            if(dto.getPhoneNumber() != null) {
                entity.setPhoneNumber(dto.getPhoneNumber());
            }else{
                entity.setPhoneNumber(entity.getPhoneNumber());
            }

            if(dto.getCpf() != null){
                entity.setCpf(dto.getCpf());
            }else{
                entity.setCpf(entity.getCpf());
            }

            if(dto.getImageUrl() != null) {
                entity.setImageUrl(dto.getImageUrl());
            }else{
                entity.setImageUrl(entity.getImageUrl());
            }
        }
    }
