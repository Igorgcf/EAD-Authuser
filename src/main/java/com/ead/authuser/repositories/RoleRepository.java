package com.ead.authuser.repositories;

import com.ead.authuser.enums.RoleType;
import com.ead.authuser.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(RoleType name);
}
