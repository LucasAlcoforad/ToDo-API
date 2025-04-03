package github.com.LucasAlcoforad.TO_DO.controller;

import github.com.LucasAlcoforad.TO_DO.dto.erro.ResponseErro;
import github.com.LucasAlcoforad.TO_DO.dto.login.LoginRequest;
import github.com.LucasAlcoforad.TO_DO.dto.login.LoginResponse;
import github.com.LucasAlcoforad.TO_DO.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Login")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(summary = "Login", description = "Logar Usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login Realizado"),
            @ApiResponse(
                    responseCode = "400", description = "Email ou Senha Invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErro.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse login = loginService.login(loginRequest);
        return ResponseEntity.ok(login);
    }

}
