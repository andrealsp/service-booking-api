package service_booking_api.core.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserSigninRequest {

    @NotBlank
    public String identifier;

    @NotBlank
    public String password;

}
