package com.ead.authuser.controllers;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.services.impl.UserServiceImpl;
import com.ead.authuser.specification.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3700)
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAllPaged(SpecificationTemplate.UserSpec spec,
                                                      @PageableDefault(page = 0, size = 12, sort = "username", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(required = false) UUID courseId){
        Page<UserDTO> page = null;
        if(courseId != null) {
            page = service.findAllPaged(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        }else{
            page = service.findAllPaged(spec, pageable);
        }
        if(!page.isEmpty()){
            for(UserDTO dto : page.toList()){
                dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable (value = "id") UUID id ){

        UserDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable (value = "id") UUID id,
                                          @Validated(UserDTO.UserView.UserPut.class)
                                          @JsonView(UserDTO.UserView.UserPut.class)
                                          @RequestBody @Valid UserDTO dto){

        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}/cpf")
    public ResponseEntity<UserDTO> updateCpf(@PathVariable (value = "id") UUID id,
                                             @RequestBody @Validated(UserDTO.UserView.CpfPut.class)
                                             @JsonView(UserDTO.UserView.CpfPut.class) @Valid UserDTO dto){

        dto = service.updateCpf(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping(value = "/{id}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable (value = "id") UUID id,
                                                 @RequestBody @Validated(UserDTO.UserView.PasswordPut.class)
                                                 @JsonView(UserDTO.UserView.PasswordPut.class) @Valid UserDTO dto){
        service.updatePassword(id, dto);
        return ResponseEntity.ok().body("Password updated successfully!");
    }

    @PutMapping(value = "/{id}/image")
    public ResponseEntity<Object> updateImage(@PathVariable (value = "id") UUID id,
                                              @RequestBody @Validated(UserDTO.UserView.ImagePut.class)
                                              @JsonView(UserDTO.UserView.ImagePut.class) @Valid UserDTO dto){

        dto = service.updateImage(id, dto);
        return ResponseEntity.ok().body("Image updated successfully!");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable (value = "id") UUID id){

        service.deleteById(id);
        return ResponseEntity.ok().body("User deleted successfully!");
    }
}