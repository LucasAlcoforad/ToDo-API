package github.com.LucasAlcoforad.TO_DO.customAnnotation;

import github.com.LucasAlcoforad.TO_DO.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValid {
    String message() default "A senha precisa ter no minimo 8 caracteres, com uma letra maiuscula e um especial";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
