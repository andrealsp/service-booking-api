package service_booking_api.core.application.service.auth.identity;

import service_booking_api.core.application.service.auth.identity.mapper.IdentityDetailsMapper;
import service_booking_api.core.application.service.auth.identity.mapper.IdentityEntityMapper;
import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.model.request.IdentitySigninRequest;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.auth.model.response.IdentityDetails;
import service_booking_api.core.domain.auth.port.in.IdentityPort;
import service_booking_api.infrastructure.persistence.user.UserRepository;
import service_booking_api.shared.exceptions.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IdentityPortImpl implements IdentityPort {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public IdentityPortImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void register(IdentitySignupRequest request) throws ApplicationException {

        validateUniqueness(request.getUsername(), request.getEmail());

        String passwordHash = passwordEncoder.encode(request.getPassword());

        IdentityEntity entity =
                IdentityEntityMapper.mapUserEntityRegistration(request, passwordHash);

        userRepository.save(entity);
    }

    @Override
    public IdentityDetails retrieveUser(IdentitySigninRequest request) throws ApplicationException {

        if (isEmail(request.getIdentifier())) {
            return IdentityDetailsMapper.map(
                    userRepository.findByEmail(request.getIdentifier())
                            .orElseThrow(() -> new ApplicationException(
                                    404, "User not found"
                            ))
            );
        }

        return IdentityDetailsMapper.map(
                userRepository.findByUsername(request.getIdentifier())
                        .orElseThrow(() -> new ApplicationException(
                                404, "User not found"
                        ))
        );

    }

    private void validateUniqueness(String username, String email) throws ApplicationException {
        if (userRepository.existsByUsername(username)) {
            throw new ApplicationException(400,
                    "Username already in use"
            );
        }

        if (userRepository.existsByEmail(email)) {
            throw new ApplicationException(400,
                    "Email already in use"
            );
        }
    }

    private boolean isEmail(String identifier) {
        return identifier.contains("@");
    }

}
