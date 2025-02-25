package com.ead.authuser.services;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.models.User;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import com.ead.authuser.services.impl.UserServiceImpl;
import com.ead.authuser.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class UserServiceImplTests {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserRepository repository;

    @Test
    public void findAllPagedShouldReturnAllUserPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<UserDTO> page = service.findAllPaged(null, pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByIdShouldReturnUserByIdWhenIdExists(){

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = service.findById(id);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        UserDTO dto = Factory.createdUserDto();

        dto = service.insert(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void updateShouldSaveObjectByIdWhenIdExists(){

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createdUserDtoToUpdate();

        dto = service.update(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void updatePasswordShouldSaveNewPasswordWhenOldPasswordIsCorrectAndIdExists(){

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createdUserDtoToUpdatePassword();

        service.updatePassword(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void updateImageShouldSaveImageWhenIdExists(){

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createUserDtoToUpdateImage();

        service.updateImage(id, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(3, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteUserWhenIdExists(){

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        service.deleteById(id);

        Assertions.assertEquals(2, repository.count());
    }
}
