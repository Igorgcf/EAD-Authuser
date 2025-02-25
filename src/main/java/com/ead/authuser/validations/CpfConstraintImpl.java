package com.ead.authuser.validations;

import com.ead.authuser.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CpfConstraintImpl implements ConstraintValidator<CpfConstraint, String> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(CpfConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {

       boolean existsByCpf = repository.existsByCpf(cpf);
        return !existsByCpf;
    }
}
