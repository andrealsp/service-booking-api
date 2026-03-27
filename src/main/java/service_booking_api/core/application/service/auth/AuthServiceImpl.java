package service_booking_api.core.application.service.auth;

import service_booking_api.core.domain.auth.model.AuthToken;
import service_booking_api.core.domain.user.model.request.UserSigninRequest;
import service_booking_api.core.domain.auth.port.in.AuthService;
import service_booking_api.core.domain.user.port.in.UserPort;
import service_booking_api.shared.exceptions.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserPort userPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProviderService jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(UserPort userPort,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProviderService jwtTokenProvider) {
        this.userPort = userPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthToken authenticate(UserSigninRequest request) throws ApplicationException {

        var user = userPort.retrieveUser(request);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Invalid password");
            throw new ApplicationException(401, "Invalid password");
        } else {
            log.info("Password matched. Generating JWT token.");
            var token = "Bearer " + jwtTokenProvider.generateToken(user);
            return AuthToken.builder().token(token).build();
        }
    }

    @Override
    public Boolean validateJwtToken(String token) throws ApplicationException {
        var newToken = token.replace("Bearer ", "");

        var username = jwtTokenProvider.extractUsername(newToken);
        var user = userPort.retrieveUser(UserSigninRequest.builder().identifier(username).build());

        return jwtTokenProvider.validateToken(user, newToken);    }

}
