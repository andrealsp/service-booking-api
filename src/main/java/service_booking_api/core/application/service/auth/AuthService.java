package service_booking_api.core.application.service.auth;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import service_booking_api.core.application.service.auth.identity.mapper.IdentityDetailsMapper;
import service_booking_api.core.application.service.auth.identity.mapper.IdentityEntityMapper;
import service_booking_api.core.application.service.user.mapper.UserEntityMapper;
import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.model.AuthToken;
import service_booking_api.core.domain.auth.model.request.IdentitySigninRequest;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.auth.port.in.AuthPort;
import service_booking_api.core.domain.auth.port.in.IdentityPort;
import service_booking_api.core.domain.user.entity.UserEntity;
import service_booking_api.core.domain.user.port.in.UserPort;
import service_booking_api.shared.exceptions.ApplicationException;

@Slf4j
@Service
public class AuthService implements AuthPort {

    private final IdentityPort identityPort;
    private final UserPort userPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProviderService jwtTokenProvider;

    public AuthService(
            IdentityPort identityPort,
            UserPort userPort,
            PasswordEncoder passwordEncoder,
            JwtTokenProviderService jwtTokenProvider
    ) {
        this.identityPort = identityPort;
        this.userPort = userPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public void registerUser(IdentitySignupRequest request) throws ApplicationException {

        // 1. Save user
        var userEntity = userPort.registerUser(request);

        // 2. Create identity
        var identityEntity = identityPort.registerIdentity(request, userEntity);

        // 3. Link and let JPA handle the rest
        userEntity.setIdentity(identityEntity);
        // No need to call a special "update" query!
    }

    @Override
    public AuthToken authenticate(IdentitySigninRequest request) throws ApplicationException {

        var identityEntity = identityPort.retrieveUser(request);
        var userEntity = userPort.retrieveUser(identityEntity.getUser().getId());

        if (!passwordEncoder.matches(request.getPassword(), identityEntity.getPassword())) {
            throw new ApplicationException(401, "Invalid password");
        }

        var token = "Bearer " + jwtTokenProvider.generateToken(
                IdentityDetailsMapper.map(identityEntity, userEntity)
        );

        return AuthToken.builder().token(token).build();
    }

    @Override
    public Boolean validateJwtToken(String token) throws ApplicationException {
        String cleanToken = token.replace("Bearer ", "");
        return jwtTokenProvider.validateToken(cleanToken);
    }
}