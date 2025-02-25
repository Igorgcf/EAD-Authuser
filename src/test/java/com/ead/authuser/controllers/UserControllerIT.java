package com.ead.authuser.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.models.User;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import com.ead.authuser.services.impl.UserServiceImpl;
import com.ead.authuser.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerIT {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void findAllPagedShouldReturnAllUserPaged() throws Exception {

        ResultActions result = mockMvc.perform(get("/users"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].username").exists());
        result.andExpect(jsonPath("$.content[0].email").exists());
        result.andExpect(jsonPath("$.content[0].fullName").exists());
        result.andExpect(jsonPath("$.content[0].cpf").exists());
        result.andExpect(jsonPath("$.content[0].phoneNumber").exists());
        result.andExpect(jsonPath("$.content[0].imageUrl").exists());
        result.andExpect(jsonPath("$.content[0].userType").exists());
        result.andExpect(jsonPath("$.content[0].userStatus").exists());
        result.andExpect(jsonPath("$.content[0].creationDate").exists());
        result.andExpect(jsonPath("$.content[0].lastUpdateDate").exists());
    }

    @Test
    public void findByIdShouldReturnUserByIdWhenIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/users/{id}", id));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.username").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.fullName").exists());
        result.andExpect(jsonPath("$.cpf").exists());
        result.andExpect(jsonPath("$.phoneNumber").exists());
        result.andExpect(jsonPath("$.imageUrl").exists());
        result.andExpect(jsonPath("$.userType").exists());
        result.andExpect(jsonPath("$.userStatus").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void registerShouldSaveObjectWhenCorrectStructure() throws Exception {

        UserDTO dto = Factory.createdUserDto();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/auth/signup")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.username").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.fullName").exists());
        result.andExpect(jsonPath("$.cpf").exists());
        result.andExpect(jsonPath("$.phoneNumber").exists());
        result.andExpect(jsonPath("$.userType").exists());
        result.andExpect(jsonPath("$.userStatus").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void updateShouldSaveObjectByIdWhenIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createdUserDtoToUpdate();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/users/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.username").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.fullName").exists());
        result.andExpect(jsonPath("$.cpf").exists());
        result.andExpect(jsonPath("$.phoneNumber").exists());
        result.andExpect(jsonPath("$.userType").exists());
        result.andExpect(jsonPath("$.userStatus").exists());
        result.andExpect(jsonPath("$.creationDate").exists());
        result.andExpect(jsonPath("$.lastUpdateDate").exists());
    }

    @Test
    public void updatePasswordShouldSaveNewPasswordWhenOldPasswordIsCorrectAndIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createdUserDtoToUpdatePassword();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/users/{id}/password", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void updateImageShouldUpdateImageByIdWhenIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        UserDTO dto = Factory.createUserDtoToUpdateImage();

        String jsonBody = mapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/users/{id}/image", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void deleteByIdShouldDeleteUserByIdWhenIdExists() throws Exception {

        Optional<User> obj = repository.findAll().stream().findFirst();
        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/users/{id}", id));

        result.andExpect(status().isOk());
    }


}
