package service_booking_api.core.application.service.auth.identity;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import service_booking_api.core.application.service.auth.identity.mapper.IdentityEntityMapper;
import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.model.request.IdentitySigninRequest;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.auth.port.in.IdentityPort;
import service_booking_api.core.domain.user.entity.UserEntity;
import service_booking_api.infrastructure.persistence.identity.IdentityRepository;
import service_booking_api.shared.exceptions.ApplicationException;

@Slf4j
@Service
public class IdentityService implements IdentityPort {

    private final IdentityRepository identityRepository;
    private final PasswordEncoder passwordEncoder;

    public IdentityService(
            IdentityRepository identityRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.identityRepository = identityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public IdentityEntity registerIdentity(
            IdentitySignupRequest request,
            UserEntity user
    ) throws ApplicationException {

        validateUniqueness(request.getUsername(), request.getContact().getEmail());

        String passwordHash = passwordEncoder.encode(request.getPassword());

        IdentityEntity entity =
                IdentityEntityMapper.fromRequest(request, passwordHash);

        entity.setUser(user);

        return identityRepository.save(entity);
    }

    @Override
    public IdentityEntity retrieveUser(IdentitySigninRequest request) throws ApplicationException {

        if (isEmail(request.getIdentifier())) {
            return identityRepository.findByEmail(request.getIdentifier())
                    .orElseThrow(() -> new ApplicationException(404, "User not found"));
        }

        return identityRepository.findByUsername(request.getIdentifier())
                .orElseThrow(() -> new ApplicationException(404, "User not found"));
    }

    private void validateUniqueness(String username, String email) throws ApplicationException {
        if (identityRepository.existsByUsername(username)) {
            throw new ApplicationException(400, "Username already in use");
        }

        if (identityRepository.existsByEmail(email)) {
            throw new ApplicationException(400, "Email already in use");
        }
    }

    private boolean isEmail(String identifier) {
        return identifier.contains("@");
    }
}