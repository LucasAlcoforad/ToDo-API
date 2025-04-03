package github.com.LucasAlcoforad.TO_DO.service;

import github.com.LucasAlcoforad.TO_DO.dto.todo.CreateTodoDTO;
import github.com.LucasAlcoforad.TO_DO.dto.todo.UpdateTodoDTO;
import github.com.LucasAlcoforad.TO_DO.entity.Todo;
import github.com.LucasAlcoforad.TO_DO.entity.User;
import github.com.LucasAlcoforad.TO_DO.exception.EntityNotExistException;
import github.com.LucasAlcoforad.TO_DO.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserService userService;

    public TodoService(TodoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }


    public Todo createTodo(CreateTodoDTO dto, JwtAuthenticationToken jwt){
        User user = userService.getUser(UUID.fromString(jwt.getName()));;
        Todo todo = dto.toEntity(user);
        return todoRepository.save(todo);
    }

    public Todo getTodo(UUID idTodo, UUID idUser){
        return todoRepository.findByIdAndUserId(idTodo, idUser).orElseThrow(
                () -> new EntityNotExistException("Todo nao existe")
        );
    }

    public Page<Todo> listTodo(
            String title,
            String description,
            boolean complete,
            Integer page,
            Integer lengthPage,
            User user) {

        Specification<Todo> specs = ((root, query, cb) -> cb.conjunction());

        if (title!=null){
            specs = specs.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"
                    )
            );
        }

        if (description!=null){
            specs = specs.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("description")),"%" + description.toLowerCase() +"%"
                    )
            );
        }

        specs = specs.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("complete"), complete)
        );

        specs = specs.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user"), user)
        );

        Pageable pageable = PageRequest.of(page, lengthPage, Sort.by("title").ascending());

        return todoRepository.findAll(specs, pageable);
    }

    public void updateTodo(UUID todoId, UpdateTodoDTO dto, UUID userId){
        Todo todo = this.getTodo(todoId, userId);

        if (dto.title()!=null){
            todo.setTitle(dto.title());
        }
        if (dto.description()!=null){
            todo.setDescription(dto.description());
        }
        if (dto.complete() != todo.isComplete()){
            todo.setComplete(dto.complete());
        }

        todoRepository.save(todo);
    }

    public void deleteTodo(UUID idTodo, UUID idUser){
        if (!todoRepository.existsByIdAndUserId(idTodo, idUser)){
            throw new EntityNotExistException("Todo nao existe");
        }
        todoRepository.deleteById(idTodo);
    }


}
