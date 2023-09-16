package ru.practicum.ewm.validator;

import ru.practicum.ewm.dto.user.NewUserRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailLengthValidator implements ConstraintValidator<EmailPartsValidation, NewUserRequest> {
    @Override
    public void initialize(EmailPartsValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(NewUserRequest newUserRequest, ConstraintValidatorContext constraintValidatorContext) {
        String[] parts = newUserRequest.getEmail().split("@");
        String localPart = parts[0];
        String domainPart = parts[1];
        if (localPart.length() > 64 || domainPart.length() > 63) {
            return false;
        }
        return true;
    }
}