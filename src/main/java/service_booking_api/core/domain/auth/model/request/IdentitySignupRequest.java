package service_booking_api.core.domain.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentitySignupRequest {

    @NotBlank
    private String fullName;

    private String givenName;

    @NotBlank
    private String identificationDocument;

    private Contact contact;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

}