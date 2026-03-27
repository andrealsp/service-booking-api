package service_booking_api.api.controller.auth;

import service_booking_api.core.domain.auth.model.AuthToken;
import service_booking_api.core.domain.auth.port.in.AuthControllerAPI;
import service_booking_api.core.domain.auth.model.request.IdentitySigninRequest;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.auth.port.in.AuthService;
import service_booking_api.core.domain.auth.port.in.IdentityPort;
import service_booking_api.shared.exceptions.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController implements AuthControllerAPI {

    private final AuthService authService;
    private final IdentityPort userService;

    public AuthController(AuthService authService,
                          IdentityPort userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> registerUser(IdentitySignupRequest request) throws ApplicationException {
        log.info("Registering identity: {}", request.getName());
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<AuthToken> generateToken(IdentitySigninRequest request) throws ApplicationException {
        log.info("Generating token for identity: {}", request.getIdentifier());
        return ResponseEntity.ok().body(authService.authenticate(request));
    }

    public ResponseEntity<Boolean> validateToken(String token) throws ApplicationException {
        log.info("Validating token");
        return ResponseEntity.ok(authService.validateJwtToken(token));
    }

}
