package ru.practicum.ewm.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailLengthValidator.class)
public @interface EmailPartsValidation {
    String message() default "Максимальная длина локальной части адреса 64 символа, доменной части - 63 символа";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}