package service_booking_api.core.domain.user.port.in;

import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.user.entity.UserEntity;
import service_booking_api.shared.exceptions.ApplicationException;

import java.util.UUID;

public interface UserPort {

    UserEntity registerUser(IdentitySignupRequest request) throws ApplicationException;

    UserEntity retrieveUser(UUID userId) throws ApplicationException;

}
