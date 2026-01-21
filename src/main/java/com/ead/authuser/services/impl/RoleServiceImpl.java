package com.ead.authuser.services.impl;

import com.ead.authuser.dto.RoleDTO;
import com.ead.authuser.enums.RoleType;
import com.ead.authuser.models.Role;
import com.ead.authuser.repositories.RoleRepository;
import com.ead.authuser.services.RoleService;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public RoleDTO findByName(RoleType name) {

        Optional<Role> obj = repository.findByName(name);
        Role entity = obj.orElseThrow(() -> new ResourceNotFoundException("Role not found: " + name));

        return new RoleDTO(entity);

    }
}
