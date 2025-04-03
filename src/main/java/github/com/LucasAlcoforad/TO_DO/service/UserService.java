package github.com.LucasAlcoforad.TO_DO.service;

import github.com.LucasAlcoforad.TO_DO.dto.user.UpdateUserDTO;
import github.com.LucasAlcoforad.TO_DO.entity.User;
import github.com.LucasAlcoforad.TO_DO.exception.EntityNotExistException;
import github.com.LucasAlcoforad.TO_DO.repository.UserRepository;
import github.com.LucasAlcoforad.TO_DO.validator.UserValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserValidator userValidator;

    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, UserValidator userValidator, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User user) {
        userValidator.validatePersistence(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getUser(UUID uuid) {
        var user = userRepository.findByIdAndActiveTrue(uuid);
        if (user.isEmpty()){
            throw new EntityNotExistException("Usuario inexistente");
        }
        return user.get();
    }

    public void putUser(UpdateUserDTO dto, String id) {
        var optional = userRepository.findByIdAndActiveTrue(UUID.fromString(id));
        if (optional.isEmpty()){
            throw new EntityNotExistException("Usuario inexistente");
        }

        User user = optional.get();
        if (!dto.email().isBlank()){
            user.setEmail(dto.email());
        }
        if (!dto.password().isBlank()){
            user.setPassword(passwordEncoder.encode(dto.password()));
        }
        if (!dto.username().isBlank()){
            user.setUsername(dto.username());
        }

        userValidator.validatePersistence(user);
        userRepository.save(user);
    }

    public void deleteUser(UUID uuid) {
        var optional = userRepository.findByIdAndActiveTrue(uuid);
        if (optional.isEmpty()){
            throw new EntityNotExistException("Usuario inexistente");
        }
        User user = optional.get();
        user.setActive(false);
        userRepository.save(user);
    }
}
