package github.com.LucasAlcoforad.TO_DO.validator;

import github.com.LucasAlcoforad.TO_DO.dto.login.LoginRequest;
import github.com.LucasAlcoforad.TO_DO.entity.User;
import github.com.LucasAlcoforad.TO_DO.exception.BadLoginException;
import github.com.LucasAlcoforad.TO_DO.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LoginValidator {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginValidator(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User validateAndReturnLogin(LoginRequest loginRequest){
        var existsUser = userRepository.findByEmailAndActiveTrue(loginRequest.email());
        if (existsUser.isEmpty()){
            throw new BadLoginException("Email ou Senha errados");
        }
        User user = existsUser.get();
        if (!bCryptPasswordEncoder.matches(loginRequest.password(), user.getPassword())){
            throw new BadLoginException("Email ou Senha errados");
        }
        return user;
    }
}
