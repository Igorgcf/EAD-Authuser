package com.ead.authuser.dto;

import com.ead.authuser.enums.RoleType;
import com.ead.authuser.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements GrantedAuthority {

    private UUID id;
    @Enumerated(EnumType.STRING)
    private RoleType name;

    public RoleDTO(Role entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return this.name.toString();
    }
}
