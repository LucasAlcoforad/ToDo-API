package github.com.LucasAlcoforad.TO_DO.validator;

import github.com.LucasAlcoforad.TO_DO.exception.UserPersistenceException;
import github.com.LucasAlcoforad.TO_DO.entity.User;
import github.com.LucasAlcoforad.TO_DO.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validatePersistence(User user) {
        var userExist = userRepository.findByEmailAndActiveTrue(user.getEmail());
        if (userExist.isPresent() && userExist.get().getId()!=user.getId()){
            throw new UserPersistenceException("Email indisponivel para uso.");
        }
    }
}
