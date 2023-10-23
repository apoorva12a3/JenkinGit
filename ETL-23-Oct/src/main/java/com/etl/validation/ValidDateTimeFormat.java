package com.etl.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Constraint(validatedBy = DateTimeFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateTimeFormat {
    String message() default "Invalid date format. Use dd-MM-yyyy HH:mm:ss";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String format() default "dd-MM-yyyy HH:mm:ss"; // Add a format attribute
}