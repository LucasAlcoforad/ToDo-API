package github.com.LucasAlcoforad.TO_DO.service;

import github.com.LucasAlcoforad.TO_DO.dto.login.LoginRequest;
import github.com.LucasAlcoforad.TO_DO.dto.login.LoginResponse;
import github.com.LucasAlcoforad.TO_DO.entity.User;
import github.com.LucasAlcoforad.TO_DO.validator.LoginValidator;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoginService {
    private final LoginValidator loginValidator;
    private static final long EXPIRATION_TIME_SECONDS = 300;
    private final JwtEncoder jwtEncoder;

    public LoginService(LoginValidator loginValidator, JwtEncoder jwtEncoder) {
        this.loginValidator = loginValidator;
        this.jwtEncoder = jwtEncoder;
    }

    public LoginResponse login(LoginRequest loginRequest){
        User user = loginValidator.validateAndReturnLogin(loginRequest);

        var claims = JwtClaimsSet.builder()
                .issuer("To-Do App")
                .subject(user.getId().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(EXPIRATION_TIME_SECONDS))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, EXPIRATION_TIME_SECONDS);
    }
}
