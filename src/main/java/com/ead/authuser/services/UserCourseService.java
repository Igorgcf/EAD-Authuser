package com.ead.authuser.services;

import com.ead.authuser.dto.CourseDTO;
import com.ead.authuser.dto.UserCourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserCourseService{

    UserCourseDTO saveSubscriptionUserInCourse(UUID userId, CourseDTO dto);

    Page<UserCourseDTO> findAllPaged(Pageable pageable);
}
