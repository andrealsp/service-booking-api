package service_booking_api.api.controller.auth;

import service_booking_api.core.domain.auth.model.AuthToken;
import service_booking_api.core.domain.auth.port.in.AuthControllerAPI;
import service_booking_api.core.domain.user.model.request.UserSigninRequest;
import service_booking_api.core.domain.user.model.request.UserSignupRequest;
import service_booking_api.core.domain.auth.port.in.AuthService;
import service_booking_api.core.domain.user.port.in.UserPort;
import service_booking_api.shared.exceptions.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController implements AuthControllerAPI {

    private final AuthService authService;
    private final UserPort userService;

    public AuthController(AuthService authService,
                          UserPort userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> registerUser(UserSignupRequest request) throws ApplicationException {
        log.info("Registering user: {}", request.getName());
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<AuthToken> generateToken(UserSigninRequest request) throws ApplicationException {
        log.info("Generating token for user: {}", request.getIdentifier());
        return ResponseEntity.ok().body(authService.authenticate(request));
    }

    public ResponseEntity<Boolean> validateToken(String token) throws ApplicationException {
        log.info("Validating token");
        return ResponseEntity.ok(authService.validateJwtToken(token));
    }

}
