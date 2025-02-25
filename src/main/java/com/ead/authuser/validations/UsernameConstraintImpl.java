package com.ead.authuser.validations;

import com.ead.authuser.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        boolean existsByUsername = repository.existsByUsername(username);
        return !existsByUsername;
    }
}
