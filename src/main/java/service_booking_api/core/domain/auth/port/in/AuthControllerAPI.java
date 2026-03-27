package service_booking_api.core.domain.auth.port.in;

import service_booking_api.core.domain.auth.model.AuthToken;
import service_booking_api.core.domain.auth.model.request.IdentitySigninRequest;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.shared.exceptions.ApplicationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@Tag(name = "AuthController", description = "Collection for authentication")
public interface AuthControllerAPI {

    @Operation(summary = "Registration Method", tags = "AuthController")
    @PostMapping("/register")
    ResponseEntity<Void> registerUser(@Valid @RequestBody IdentitySignupRequest request) throws ApplicationException;

    @Operation(summary = "GenerateToken Method", tags = "AuthController")
    @PostMapping("/login")
    ResponseEntity<AuthToken> generateToken(@Valid @RequestBody IdentitySigninRequest request) throws ApplicationException;

    @Operation(summary = "Validate Method", tags = "AuthController")
    @GetMapping("/validate")
    ResponseEntity<Boolean> validateToken(@RequestHeader(value = "Authorization") String token) throws ApplicationException;

}
