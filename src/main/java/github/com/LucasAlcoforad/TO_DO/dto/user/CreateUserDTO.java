package github.com.LucasAlcoforad.TO_DO.dto.user;

import github.com.LucasAlcoforad.TO_DO.customAnnotation.PasswordValid;
import github.com.LucasAlcoforad.TO_DO.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
        @Email @NotBlank
        String email,
        @PasswordValid @NotBlank
        String password,
        @NotBlank
        String username) {

    public User toEntity(){
        return new User(this.email,this.password,this.username);
    }

}
