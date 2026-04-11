package service_booking_api.core.domain.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentityDetails {

    private UUID id;
    private String givenName;
    private String username;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

}
