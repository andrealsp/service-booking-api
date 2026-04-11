package service_booking_api.core.application.service.auth.identity.mapper;

import org.springframework.stereotype.Component;
import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.entity.Role;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.user.entity.UserEntity;
import service_booking_api.shared.utils.GetLocalTime;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public final class IdentityEntityMapper {

    public static IdentityEntity fromRequest(IdentitySignupRequest request, String passwordHash) {

        return IdentityEntity.builder()
                .username(request.getUsername())
                .email(request.getContact().getEmail())
                .password(passwordHash)
                .role(parseRole(request.getRole()))
                .createdAt(OffsetDateTime.now())
                .build();
    }

    private static Role parseRole(String roleStr) {
        String normalized = roleStr.trim();

        for (Role r : Role.values()) {
            if (r.name().equalsIgnoreCase(normalized)) {
                return r;
            }
        }

        for (Role r : Role.values()) {
            if (r.toString().equalsIgnoreCase(normalized)) {
                return r;
            }
        }
        return null;
    }
}