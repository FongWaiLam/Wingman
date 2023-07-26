package com.fwl.unmannedstore.security.entity.validation;

import com.fwl.unmannedstore.security.entity.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidRoleValidator implements ConstraintValidator<ValidRole, Role> {
    @Override
    public boolean isValid(Role value, ConstraintValidatorContext context) {
        return value != null && (value == Role.ROLE_ADMIN || value == Role.ROLE_USER);
    }
}
