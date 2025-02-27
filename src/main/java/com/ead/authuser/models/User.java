package com.ead.authuser.models;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 77)
    private String username;

    @Column(nullable = false, unique = true, length = 77)
    private String email;

    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 177)
    private String fullName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(length = 27)
    private String phoneNumber;

    @Column(length = 27)
    private String cpf;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdateDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserCourse> userCourses;

    public User(){
    }

    public User(UUID id, String username, String email, String password, String fullName, UserStatus userStatus, UserType userType,
                String phoneNumber, String cpf, String imageUrl, LocalDateTime creationDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.userStatus = userStatus;
        this.userType = userType;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.imageUrl = imageUrl;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public UserCourse convertToUserCourse(UUID courseId) {
        return new UserCourse(null, courseId, this );
    }
}
