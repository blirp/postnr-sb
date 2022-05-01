package org.larma.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<Numeric, String> {
    @Override
    public void initialize(
        Numeric constraintAnnotation) 
    {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(
        final String s, 
        final ConstraintValidatorContext constraintValidatorContext) 
    {
        if (s == null || s.trim().length() == 0 ) {
            return true;
        }

        boolean valid = s.chars().allMatch(Character::isDigit);

        if (!valid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Fikk '" + s + "' der numerisk verdi var forventet.")
                    .addConstraintViolation();
        }

        return valid;
    }
}
