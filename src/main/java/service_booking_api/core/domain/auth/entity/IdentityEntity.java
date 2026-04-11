package service_booking_api.core.domain.auth.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import service_booking_api.core.domain.user.entity.UserEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@Entity
@Table(name = "identities")
public class IdentityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // getters/setters
}