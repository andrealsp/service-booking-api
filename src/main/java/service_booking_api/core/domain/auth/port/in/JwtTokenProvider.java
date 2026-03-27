package service_booking_api.core.domain.auth.port.in;

import service_booking_api.core.domain.user.model.response.UserDetails;
import service_booking_api.shared.exceptions.ApplicationException;

public interface JwtTokenProvider {

    String generateToken(UserDetails userDetails) throws ApplicationException;

}
