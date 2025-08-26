package com.ead.authuser.dto;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;
import com.ead.authuser.validations.CpfConstraint;
import com.ead.authuser.validations.EmailConstraint;
import com.ead.authuser.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends RepresentationModel<UserDTO> {

    public interface UserView{
        public static interface RegistrationPost {}
        public static interface UserPut {}
        public static interface CpfPut {}
        public static interface PasswordPut {}
        public static interface ImagePut {}
    }

    private UUID id;

    @NotBlank(groups = UserView.RegistrationPost.class, message = "The username field is mandatory and blanks are not allowed.")
    @Size(groups = UserView.RegistrationPost.class, min = 7, max = 27, message = "Minimum character value allowed is 07 and the maximum is 27.")
    @UsernameConstraint(groups = UserView.RegistrationPost.class, message = "The username is already in use.")
    @JsonView(UserView.RegistrationPost.class)
    private String username;

    @NotBlank(groups = UserView.RegistrationPost.class, message = "The e-mail field is mandatory and blanks are not allowed.")
    @Email(groups = UserView.RegistrationPost.class, message = "Email must have an acceptable standard.")
    @EmailConstraint(groups = UserView.RegistrationPost.class, message = "The e-mail is already in use.")
    @JsonView(UserView.RegistrationPost.class)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class}, min = 7, max = 37, message = "Minimum character value allowed is 07 and the maximum is 37.")
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class}, message = "The password field is mandatory and blanks are not allowed.")
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;

    @Size(groups = UserView.PasswordPut.class, min = 7, max = 37, message = "Minimum character value allowed is 07 and the maximum is 37.")
    @NotBlank(groups = UserView.PasswordPut.class, message = "The old password field is mandatory and blanks are not allowed.")
    @JsonView(UserView.PasswordPut.class)
    private String oldPassword;

    @Size(groups = {UserView.RegistrationPost.class, UserView.UserPut.class}, min = 7, max = 57, message = "Minimum character value allowed is 07 and the maximum is 57.")
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;

    @Size(groups = {UserView.RegistrationPost.class, UserView.UserPut.class}, min = 11, max = 57, message = "Minimum character value allowed is 07 and the maximum is 57.")
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.CpfPut.class}, message = "The CPF field is mandatory and and blanks are not allowed.")
    @CpfConstraint(groups = {UserView.RegistrationPost.class, UserView.CpfPut.class}, message = "The CPF is already in use.")
    @Size(groups = {UserView.RegistrationPost.class, UserView.CpfPut.class}, min = 7, max = 27, message = "Minimum character value allowed is 07 and the maximum is 27.")
    @JsonView({UserView.RegistrationPost.class, UserView.CpfPut.class})
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class, message = "The image field is mandatory and blanks are not allowed.")
    @JsonView(UserView.ImagePut.class)
    private String imageUrl;

    private UserStatus userStatus;
    private UserType userType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    public EventDTO convertToUserDTOEventDTO() {

        EventDTO dto =  new EventDTO();
        dto.setUsername(this.username);
        dto.setEmail(this.email);
        dto.setFullName(this.fullName);
        dto.setUserStatus(this.userStatus.toString());
        dto.setUserType(this.userType.toString());
        dto.setPhoneNumber(this.phoneNumber);
        dto.setCpf(this.cpf);
        dto.setImageUrl(this.imageUrl);
        return dto;
    }

    public UserDTO(){
    }

    public UserDTO(String oldPassword, String password, LocalDateTime lastUpdateDate) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.lastUpdateDate = lastUpdateDate;
    }

    public UserDTO(String imageUrl, LocalDateTime lastUpdateDate) {
        this.imageUrl = imageUrl;
        this.lastUpdateDate = lastUpdateDate;
    }

    public UserDTO(UUID id, String username, String email, String password, String fullName, String phoneNumber,
                   String cpf, String imageUrl, UserStatus userStatus, UserType userType, LocalDateTime creationDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.imageUrl = imageUrl;
        this.userStatus = userStatus;
        this.userType = userType;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public UserDTO(User entity){
        id = entity.getId();
        username = entity.getUsername();
        email = entity.getEmail();
        password = entity.getPassword();
        fullName = entity.getFullName();
        userStatus = entity.getUserStatus();
        userType = entity.getUserType();
        phoneNumber = entity.getPhoneNumber();
        cpf = entity.getCpf();
        imageUrl = entity.getImageUrl();
        creationDate = entity.getCreationDate();
        lastUpdateDate = entity.getLastUpdateDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}