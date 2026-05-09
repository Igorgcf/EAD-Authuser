package com.ead.authuser.config.security;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.models.User;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> obj = repository.findByUsername(username);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return UserDetailsImpl.buildFromEntity(entity);
    }

    @Transactional
    public UserDetails loadUserById(UUID userId) throws AuthenticationCredentialsNotFoundException {

        Optional<User> obj = repository.findById(userId);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("User not found with userId: " + userId));
        return UserDetailsImpl.buildFromEntity(entity);
    }
}
