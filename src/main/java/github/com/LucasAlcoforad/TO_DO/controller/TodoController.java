package github.com.LucasAlcoforad.TO_DO.controller;

import github.com.LucasAlcoforad.TO_DO.dto.erro.ResponseErro;
import github.com.LucasAlcoforad.TO_DO.dto.todo.CreateTodoDTO;
import github.com.LucasAlcoforad.TO_DO.dto.todo.UpdateTodoDTO;
import github.com.LucasAlcoforad.TO_DO.entity.Todo;
import github.com.LucasAlcoforad.TO_DO.entity.User;
import github.com.LucasAlcoforad.TO_DO.service.TodoService;
import github.com.LucasAlcoforad.TO_DO.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
@Tag(name = "ToDo's")
public class TodoController {

    private final TodoService todoService;

    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Cadastrar Novo ToDo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "ToDo Criado"),
            @ApiResponse(responseCode = "401", description = "Jwt invalido")
    })
    public ResponseEntity<Void> createTodo(@Valid @RequestBody CreateTodoDTO dto,
                                           JwtAuthenticationToken jwt){
        Todo todo = todoService.createTodo(dto, jwt);
        URI uri = URI.create(todo.getId().toString());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Read", description = "Buscar ToDo por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ToDo Criado"),
            @ApiResponse(responseCode = "401", description = "Jwt invalido"),
            @ApiResponse(
                    responseCode = "404", description = "ToDo Nao Encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErro.class))
            )
    })
    public ResponseEntity<UpdateTodoDTO> getTodo(@PathVariable("id") String id,
                                                 JwtAuthenticationToken jwt){
        Todo todo = todoService.getTodo(UUID.fromString(id), UUID.fromString(jwt.getName()));
        UpdateTodoDTO dto = new UpdateTodoDTO(
                todo.getTitle(),
                todo.getDescription(),
                todo.isComplete()
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "List", description = "Listar ToDo's")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Todo's Listados"),
            @ApiResponse(responseCode = "401", description = "Jwt invalido")
    })
    public ResponseEntity<Page<UpdateTodoDTO>> listTodo(
            @RequestParam(value = "title", required = false)
            String title,
            @RequestParam(value = "description", required = false)
            String description,
            @RequestParam(value = "complete", required = false, defaultValue = "false")
            boolean complete,
            @RequestParam(value = "page", required = false, defaultValue = "0")
            Integer page,
            @RequestParam(value = "length-page", required = false, defaultValue = "10")
            Integer lengthPage,
            JwtAuthenticationToken jwt
    ){
        User user = userService.getUser(UUID.fromString(jwt.getName()));
        var list = todoService.listTodo(
                title, description, complete, page, lengthPage, user)
                .map(todo -> new UpdateTodoDTO(todo.getTitle(), todo.getDescription(), todo.isComplete()));

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Update", description = "Atualizar ToDo por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "ToDo Atualizado"),
            @ApiResponse(responseCode = "401", description = "Jwt invalido"),
            @ApiResponse(
                    responseCode = "404", description = "ToDo Nao Encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErro.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") String id,
                                           @RequestBody UpdateTodoDTO dto,
                                           JwtAuthenticationToken jwt){
        todoService.updateTodo(UUID.fromString(id), dto, UUID.fromString(jwt.getName()));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete", description = "Deletar ToDo por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "ToDo Deletado"),
            @ApiResponse(responseCode = "401", description = "Jwt invalido"),
            @ApiResponse(
                    responseCode = "404", description = "ToDo Nao Encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErro.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") String id,
                                           JwtAuthenticationToken jwt){
        todoService.deleteTodo(UUID.fromString(id), UUID.fromString(jwt.getName()));
        return ResponseEntity.noContent().build();
    }
}
