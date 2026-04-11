package service_booking_api.core.application.service.user;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import service_booking_api.core.application.service.user.mapper.UserEntityMapper;
import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.user.entity.UserEntity;
import service_booking_api.core.domain.user.port.in.UserPort;
import service_booking_api.infrastructure.persistence.user.UserRepository;
import service_booking_api.shared.exceptions.ApplicationException;

import java.util.UUID;

@Service
public class UserService implements UserPort {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =========================
    // 🧾 CREATE USER
    // =========================
    @Override
    @Transactional
    public UserEntity registerUser(IdentitySignupRequest request) throws ApplicationException {

        validateUniqueness(request.getIdentificationDocument());

        UserEntity entity = UserEntityMapper.fromRequest(request);

        return userRepository.save(entity);
    }

    // =========================
    // 🔎 GET USER
    // =========================
    @Override
    public UserEntity retrieveUser(UUID userId) throws ApplicationException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(404, "User not found"));
    }

    private void validateUniqueness(String identificationDocument) throws ApplicationException {
        if (userRepository.existsByIdentificationDocument(identificationDocument)) {
            throw new ApplicationException(400, "User already registered");
        }
    }
}