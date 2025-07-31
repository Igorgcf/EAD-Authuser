package com.ead.authuser.controllers;

import com.ead.authuser.clients.UserClient;
import com.ead.authuser.dto.CourseDTO;
import com.ead.authuser.dto.UserCourseDTO;
import com.ead.authuser.services.UserCourseService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3700)
public class UserCourseController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private UserCourseService service;

    @GetMapping(value = "/users/courses")
    public ResponseEntity<Page<UserCourseDTO>> findAllPaged(@PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<UserCourseDTO> page = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/users/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> findAllCoursesByUser(@PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                @PathVariable(value = "userId") UUID userId){

        Page<CourseDTO> page = userClient.findAllCoursesByUser(userId, pageable);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping(value = "/users/{userId}/courses/subscription")
    public ResponseEntity<UserCourseDTO> saveSubscriptionUserInCourse(@PathVariable (value = "userId") UUID userId,
                                                                          @Validated(CourseDTO.CourseView.registrationPost.class)
                                                                          @RequestBody @Valid CourseDTO dto){

        UserCourseDTO response = service.saveSubscriptionUserInCourse(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(value = "/users/courses/{courseId}")
    public ResponseEntity<Object> deleteUserCourseByCourse(@PathVariable(value = "courseId") UUID courseId){
        service.deleteUserCourseByCourse(courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User course deleted successfully");
    }
    }