package service_booking_api.core.domain.auth.port.in;

import service_booking_api.core.domain.auth.model.AuthToken;
import service_booking_api.core.domain.auth.model.request.IdentitySigninRequest;
import service_booking_api.shared.exceptions.ApplicationException;

public interface AuthService {

    AuthToken authenticate(IdentitySigninRequest request) throws ApplicationException;

    Boolean validateJwtToken(String token) throws ApplicationException;

}
