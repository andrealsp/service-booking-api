package service_booking_api.core.application.service.user.mapper;

import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.model.request.IdentitySignupRequest;
import service_booking_api.core.domain.user.entity.UserEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

public class UserEntityMapper {

    public static UserEntity fromRequest(IdentitySignupRequest request) {
        return UserEntity.builder()
                .fullName(request.getFullName())
                .givenName(request.getGivenName())
                .identificationDocument(request.getIdentificationDocument())
                .address(request.getContact().getAddress())
                .email(request.getContact().getEmail())
                .phoneNumber(request.getContact().getPhoneNumber())
                .isActive(true)
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
