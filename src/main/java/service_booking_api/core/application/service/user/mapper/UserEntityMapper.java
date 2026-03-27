package service_booking_api.core.application.service.user.mapper;

import org.springframework.stereotype.Component;
import service_booking_api.core.domain.user.entity.UserEntity;
import service_booking_api.core.domain.user.model.Role;
import service_booking_api.core.domain.user.model.request.UserSignupRequest;
import service_booking_api.shared.utils.GetLocalTime;

@Component
public final class UserEntityMapper {

    public static UserEntity mapUserEntityRegistration(UserSignupRequest request, String passwordHash) {
        return UserEntity.builder()
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