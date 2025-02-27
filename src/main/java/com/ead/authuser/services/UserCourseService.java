package com.ead.authuser.services;

import com.ead.authuser.dto.CourseDTO;
import com.ead.authuser.dto.UserCourseDTO;

import java.util.UUID;

public interface UserCourseService{

    UserCourseDTO saveSubscriptionUserInCourse(UUID userId, CourseDTO dto);
}
