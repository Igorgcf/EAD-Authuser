package com.ead.authuser.dto;

import com.ead.authuser.enums.Level;
import com.ead.authuser.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CourseDTO {

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
