package com.ead.authuser.repositories;

import com.ead.authuser.models.User;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import com.ead.authuser.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @Test
    public void findAllShouldReturnAllUserPaged(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<User> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
        Assertions.assertFalse(page.isEmpty());
    }

    @Test
    public void findByIdShouldReturnUserByIdWhenIdExists(){

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        Optional<User> optional = repository.findById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isEmpty());
    }

    @Test
    public void saveShouldInsertObjectWhenCorrectStructure(){

        User entity = Factory.createdUser();

        repository.save(entity);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(4, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteUserByIdWhenIdExists(){

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        repository.deleteById(id);

        Assertions.assertEquals(2, repository.count());
    }
}
