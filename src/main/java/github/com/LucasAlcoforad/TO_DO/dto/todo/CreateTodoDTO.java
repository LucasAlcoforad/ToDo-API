package github.com.LucasAlcoforad.TO_DO.dto.todo;

import github.com.LucasAlcoforad.TO_DO.entity.Todo;
import github.com.LucasAlcoforad.TO_DO.entity.User;
import jakarta.validation.constraints.NotBlank;

public record CreateTodoDTO(@NotBlank
                            String title,
                            String description){

    public Todo toEntity(User user){
        return new Todo(this.title, this.description, user);
    }

}
