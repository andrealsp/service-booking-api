package service_booking_api.core.domain.auth.model.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Builder
public class IdentityDetails {

    private UUID id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private LocalDateTime createdAt;

}
