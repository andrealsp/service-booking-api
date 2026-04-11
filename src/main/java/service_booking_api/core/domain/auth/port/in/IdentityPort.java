package service_booking_api.core.domain.auth.port.in;

import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.model.request.IdentitySigninRequest;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.auth.model.response.IdentityDetails;
import service_booking_api.core.domain.user.entity.UserEntity;
import service_booking_api.shared.exceptions.ApplicationException;

public interface IdentityPort {

    IdentityEntity registerIdentity(IdentitySignupRequest request, UserEntity user) throws ApplicationException;

    IdentityEntity retrieveUser(IdentitySigninRequest request) throws ApplicationException;

}
