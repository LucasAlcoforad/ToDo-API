package github.com.LucasAlcoforad.TO_DO.controller;

import github.com.LucasAlcoforad.TO_DO.dto.erro.ResponseErro;
import github.com.LucasAlcoforad.TO_DO.dto.user.CreateUserDTO;
import github.com.LucasAlcoforad.TO_DO.dto.user.ResponseUserDTO;
import github.com.LucasAlcoforad.TO_DO.dto.user.UpdateUserDTO;
import github.com.LucasAlcoforad.TO_DO.entity.User;
import github.com.LucasAlcoforad.TO_DO.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "Users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Cadastrar novo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastro Realizado"),
            @ApiResponse(
                    responseCode = "422", description = "Erro de Validacao",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErro.class))
            ),
            @ApiResponse(
                    responseCode = "409", description = "Email de Usuario Indisponivel",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErro.class))
            )
    })
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserDTO dto) {
        User user = dto.toEntity();
        userService.createUser(user);
        return ResponseEntity.created(URI.create(user.getId().toString())).build();
    }

    @GetMapping
    @Operation(summary = "Buscar", description = "Dados do Usuario Logado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario Encontrado",
                    content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ResponseUserDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Usuario Nao Encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErro.class))
            ),
            @ApiResponse(responseCode = "401", description = "Jwt invalido")
    })
    public ResponseEntity<ResponseUserDTO> getUser(JwtAuthenticationToken jwt){
        User user = userService.getUser(UUID.fromString(jwt.getName()));

        ResponseUserDTO dto = new ResponseUserDTO(
                user.getEmail(),
                user.getUsername(),
                user.getCreateTimeStamp(),
                user.getUpdateTimeStamp());

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @Operation(summary = "Atualizar", description = "Atualiza os Dados do Usuario Logado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Dados Atualizados"),
            @ApiResponse(
                    responseCode = "404", description = "Usuario Nao Encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErro.class))
            ),
            @ApiResponse(
                    responseCode = "409", description = "Email de Usuario Indisponivel",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErro.class))
            ),
            @ApiResponse(responseCode = "401", description = "Jwt invalido")
    })
    public ResponseEntity<Void> putUser(JwtAuthenticationToken jwt,
                                        @Valid @RequestBody UpdateUserDTO dto){
        userService.putUser(dto, jwt.getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "Deletar", description = "Deleta o Usuario Logado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario Deletado"),
            @ApiResponse(
                    responseCode = "404", description = "Usuario Nao Encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErro.class))
            ),
            @ApiResponse(responseCode = "401", description = "Jwt invalido")
    })
    public ResponseEntity<Void> deleteUser(JwtAuthenticationToken jwt){
        userService.deleteUser(UUID.fromString(jwt.getName()));
        return ResponseEntity.noContent().build();
    }
}
