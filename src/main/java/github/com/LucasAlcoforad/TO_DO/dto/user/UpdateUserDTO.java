package github.com.LucasAlcoforad.TO_DO.dto.user;

import github.com.LucasAlcoforad.TO_DO.customAnnotation.PasswordValid;
import jakarta.validation.constraints.Email;

public record UpdateUserDTO(
        @Email
        String email,
        @PasswordValid
        String password,
        String username
) {
}
