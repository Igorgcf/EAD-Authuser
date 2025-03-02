package com.ead.authuser.dto;

import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserCourseDTO {

    private UUID id;
    private UUID courseId;
    private UserDTO userDTO;

    public UserCourseDTO(){
    }

    public UserCourseDTO(UUID id, UUID courseId, User user) {
        this.id = id;
        this.courseId = courseId;
        this.userDTO = new UserDTO(user);
    }

    public UserCourseDTO(UserCourse entity){

        id = entity.getId();
        courseId = entity.getCourseId();
        userDTO = new UserDTO(entity.getUser());
    }
}
