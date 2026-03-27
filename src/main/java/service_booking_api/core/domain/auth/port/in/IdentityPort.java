package service_booking_api.core.domain.auth.port.in;

import service_booking_api.core.domain.auth.model.request.IdentitySigninRequest;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.auth.model.response.IdentityDetails;
import service_booking_api.shared.exceptions.ApplicationException;

public interface IdentityPort {

    void register(IdentitySignupRequest request) throws ApplicationException;

    IdentityDetails retrieveUser(IdentitySigninRequest request) throws ApplicationException;

}
