package com.ead.authuser.dto;

import com.ead.authuser.enums.Level;
import com.ead.authuser.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CourseDTO {

    public interface CourseView{
        public static interface registrationPost{}
    }

    @JsonView(CourseView.registrationPost.class)
    @NotNull(groups = CourseView.registrationPost.class, message = "The field is is mandatory.")
    private UUID id;

    private String name;
    private String description;
    private String imageUrl;
    private Status status;
    private Level courseLevel;
    private UUID instructorId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    private List<ModuleDTO> modules = new ArrayList<>();

}
