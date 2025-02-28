package com.ead.authuser.controllers;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.services.InstructorService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@RestController
@RequestMapping(value = "/instructors")
public class InstructorController {

    @Autowired
    private InstructorService service;

    @PostMapping(value = "/subscription")
    public ResponseEntity<UserDTO> insertInstructor(@JsonView(UserDTO.UserView.RegistrationPost.class)
                                                    @Validated(UserDTO.UserView.RegistrationPost.class)
                                                    @RequestBody @Valid UserDTO dto){

        dto = service.insertInstructor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateUserToInstructor(@PathVariable(value = "id") UUID id){

        service.updateUserToInstructor(id);
        return ResponseEntity.ok().body("User updated to instructor successfully.");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(value = "id") UUID id){

        UserDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity<UserDTO> updateInstructor(@PathVariable(value = "id") UUID id,
                                                    @JsonView(UserDTO.UserView.UserPut.class)
                                                    @Validated(UserDTO.UserView.UserPut.class)
                                                    @RequestBody @Valid UserDTO dto){

        dto = service.updateInstructor(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}/cpf")
    public ResponseEntity<UserDTO> updateCpf(@PathVariable(value = "id") UUID id,
                                             @JsonView(UserDTO.UserView.CpfPut.class)
                                             @Validated(UserDTO.UserView.CpfPut.class)
                                             @RequestBody @Valid UserDTO dto){

        dto = service.updateCpf(id, dto);
        return ResponseEntity.ok().body(dto);

    }

    @PutMapping(value = "/{id}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "id") UUID id,
                                                 @JsonView(UserDTO.UserView.PasswordPut.class)
                                                 @Validated(UserDTO.UserView.PasswordPut.class)
                                                 @RequestBody @Valid UserDTO dto){

        service.updatePassword(id, dto);
        return ResponseEntity.ok().body("Password updated successfully.");
    }

    @PutMapping(value = "/{id}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "id") UUID id,
                                              @JsonView(UserDTO.UserView.ImagePut.class)
                                              @Validated(UserDTO.UserView.ImagePut.class)
                                              @RequestBody @Valid UserDTO dto){

        service.updateImage(id, dto);
        return ResponseEntity.ok().body("Image updated successfully.");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable (value = "id") UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("Instructor deleted successfully.");
    }
}
