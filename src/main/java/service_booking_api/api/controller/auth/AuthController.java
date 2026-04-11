package service_booking_api.api.controller.auth;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import service_booking_api.core.domain.auth.model.AuthToken;
import service_booking_api.core.domain.auth.model.request.IdentitySigninRequest;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.auth.port.in.AuthControllerAPI;
import service_booking_api.core.domain.auth.port.in.AuthPort;
import service_booking_api.shared.exceptions.ApplicationException;

@Slf4j
@RestController
public class AuthController implements AuthControllerAPI {

    private final AuthPort authPort;

    public AuthController(AuthPort authPort) {
        this.authPort = authPort;
    }

    @Override
    public ResponseEntity<Void> registerUser(IdentitySignupRequest request)
            throws ApplicationException {

        log.info("Registering user: {}", request.getFullName());

        authPort.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Override
    public ResponseEntity<AuthToken> generateToken(IdentitySigninRequest request)
            throws ApplicationException {

        log.info("Generating token for: {}", request.getIdentifier());

        return ResponseEntity.ok(authPort.authenticate(request));
    }

    @Override
    public ResponseEntity<Boolean> validateToken(String token)
            throws ApplicationException {

        log.info("Validating token");

        return ResponseEntity.ok(authPort.validateJwtToken(token));
    }
}