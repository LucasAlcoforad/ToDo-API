package github.com.LucasAlcoforad.TO_DO.validator;

import github.com.LucasAlcoforad.TO_DO.customAnnotation.PasswordValid;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()){
            return false;
        }
        return s.matches("^(?=.*[A-Z])(?=.*[\\W_]).{8,}$");
    }
}
