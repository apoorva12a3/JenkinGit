package com.etl.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatValidator implements ConstraintValidator<ValidDateTimeFormat, Date> {
    private final String dateFormat = "dd-MM-yyyy HH:mm:ss";

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Null values are considered invalid.
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {
            // Format the Date as a String and then parse it to check the format
            String dateAsString = sdf.format(value);
            Date parsedDate = sdf.parse(dateAsString);
            return parsedDate.equals(value); // Check if the parsed Date is equal to the original Date
        } catch (ParseException e) {
            return false;
        }
    }
}