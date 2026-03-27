package service_booking_api.core.domain.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IdentitySigninRequest {

    @NotBlank
    public String identifier;

    @NotBlank
    public String password;

}
