package github.com.LucasAlcoforad.TO_DO.repository;

import github.com.LucasAlcoforad.TO_DO.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndActiveTrue(String email);

    Optional<User> findByIdAndActiveTrue(UUID id);

    boolean existsByIdAndActiveTrue(UUID id);
}
