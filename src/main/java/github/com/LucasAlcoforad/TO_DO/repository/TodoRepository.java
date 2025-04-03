package github.com.LucasAlcoforad.TO_DO.repository;

import github.com.LucasAlcoforad.TO_DO.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID>, JpaSpecificationExecutor<Todo> {
    Optional<Todo> findByIdAndUserId(UUID todoId, UUID userId);
    boolean existsByIdAndUserId(UUID todoId, UUID userId);
}
