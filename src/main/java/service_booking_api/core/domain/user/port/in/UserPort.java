package service_booking_api.core.domain.user.port.in;

import service_booking_api.core.domain.user.model.request.UserSigninRequest;
import service_booking_api.core.domain.user.model.request.UserSignupRequest;
import service_booking_api.core.domain.user.model.response.UserDetails;
import service_booking_api.shared.exceptions.ApplicationException;

public interface UserPort {

    void register(UserSignupRequest request) throws ApplicationException;

    UserDetails retrieveUser(UserSigninRequest request) throws ApplicationException;

}
