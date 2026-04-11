package service_booking_api.core.domain.auth.port.in;

import service_booking_api.core.domain.auth.model.response.IdentityDetails;
import service_booking_api.shared.exceptions.ApplicationException;

public interface JwtTokenProvider {

    String generateToken(IdentityDetails identityDetails) throws ApplicationException;
    Boolean validateToken(String token) throws ApplicationException;
    String extractUsername(String token) throws ApplicationException;

}
