package service_booking_api.core.application.service.auth.identity.mapper;

import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.model.response.IdentityDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IdentityDetailsMapper {

    public static IdentityDetails map(IdentityEntity user) {

        return IdentityDetails.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().name())))
                .createdAt(user.getCreatedAt())
                .build();

    }

}