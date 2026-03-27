package service_booking_api.core.application.service.auth.identity.mapper;

import org.springframework.stereotype.Component;
import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.entity.Role;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.shared.utils.GetLocalTime;

@Component
public final class IdentityEntityMapper {

    public static IdentityEntity mapUserEntityRegistration(IdentitySignupRequest request, String passwordHash) {
        return IdentityEntity.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(preparePassword(passwordHash))
                .role(parseRole(request.getRole()))
                .createdAt(GetLocalTime.getLocalTime())
                .build();
    }

    private static String preparePassword(String passwordHash) {
        return "{bcrypt}" + passwordHash;
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

    private static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

}