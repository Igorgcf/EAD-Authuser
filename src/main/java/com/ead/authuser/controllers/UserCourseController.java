package com.ead.authuser.controllers;

import com.ead.authuser.clients.UserClient;
import com.ead.authuser.dto.CourseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3700)
public class UserCourseController {

    @Autowired
    private UserClient userClient;

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping(value = "/users/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> findAllCoursesByUser(@PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                @PathVariable(value = "userId") UUID userId,
                                                                @RequestHeader("Authorization") String token){

        Page<CourseDTO> page = userClient.findAllCoursesByUser(userId, pageable, token);
        return ResponseEntity.ok().body(page);
    }
    }