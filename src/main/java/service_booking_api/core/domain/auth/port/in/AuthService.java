package service_booking_api.core.domain.auth.port.in;

import service_booking_api.core.domain.auth.model.AuthToken;
import service_booking_api.core.domain.user.model.request.UserSigninRequest;
import service_booking_api.shared.exceptions.ApplicationException;

public interface AuthService {

    AuthToken authenticate(UserSigninRequest request) throws ApplicationException;

    Boolean validateJwtToken(String token) throws ApplicationException;

}
