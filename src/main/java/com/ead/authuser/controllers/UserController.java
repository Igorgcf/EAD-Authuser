package com.ead.authuser.controllers;

import com.ead.authuser.config.security.UserDetailsImpl;
import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.services.impl.UserServiceImpl;
import com.ead.authuser.specification.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3700)
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAllPaged(SpecificationTemplate.UserSpec spec,

                                                      @PageableDefault(page = 0, size = 12, sort = "username", direction = Sort.Direction.ASC) Pageable pageable,
                                                      Authentication authentication) {

        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.info("Authenticated user: {}", userDetails);

        Page<UserDTO> page = service.findAllPaged(spec, pageable);
        if (!page.isEmpty()) {
            for (UserDTO dto : page.toList()) {
                dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(page);
    }


    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(value = "id") UUID id) {
            UserDTO dto = service.findById(id);
            return ResponseEntity.ok().body(dto);
        }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable (value = "id") UUID id,
                                          @Validated(UserDTO.UserView.UserPut.class)
                                          @JsonView(UserDTO.UserView.UserPut.class)
                                          @RequestBody @Valid UserDTO dto){

        dto = service.updater(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping(value = "/{id}/cpf")
    public ResponseEntity<UserDTO> updateCpf(@PathVariable (value = "id") UUID id,
                                             @RequestBody @Validated(UserDTO.UserView.CpfPut.class)
                                             @JsonView(UserDTO.UserView.CpfPut.class) @Valid UserDTO dto){

        dto = service.updaterCpf(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping(value = "/{id}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable (value = "id") UUID id,
                                                 @RequestBody @Validated(UserDTO.UserView.PasswordPut.class)
                                                 @JsonView(UserDTO.UserView.PasswordPut.class) @Valid UserDTO dto){
        service.updatePassword(id, dto);
        return ResponseEntity.ok().body("Password updated successfully!");
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping(value = "/{id}/image")
    public ResponseEntity<Object> updateImage(@PathVariable (value = "id") UUID id,
                                              @RequestBody @Validated(UserDTO.UserView.ImagePut.class)
                                              @JsonView(UserDTO.UserView.ImagePut.class) @Valid UserDTO dto){

        dto = service.updaterImage(id, dto);
        return ResponseEntity.ok().body("Image updated successfully!");
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable (value = "id") UUID id){

        service.deleterById(id);
        return ResponseEntity.ok().body("User deleted successfully!");
    }
}