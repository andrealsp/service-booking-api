package service_booking_api.core.application.service.auth.identity.mapper;

import service_booking_api.core.domain.auth.entity.IdentityEntity;
import service_booking_api.core.domain.auth.model.response.IdentityDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import service_booking_api.core.domain.user.entity.UserEntity;

import java.util.List;

@Component
public class IdentityDetailsMapper {

    public static IdentityDetails map(IdentityEntity identityEntity, UserEntity userEntity) {

        return IdentityDetails.builder()
                .id(identityEntity.getId())
                .givenName(userEntity.getGivenName())
                .email(identityEntity.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority(identityEntity.getRole().name())))
                .build();

    }

}