package service_booking_api.core.application.service.user.mapper;

import service_booking_api.core.domain.user.entity.UserEntity;
import service_booking_api.core.domain.user.model.response.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsMapper {

    public static UserDetails map(UserEntity user) {

        return UserDetails.builder()
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