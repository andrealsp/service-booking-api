package service_booking_api.core.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import service_booking_api.core.domain.auth.entity.IdentityEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private IdentityEntity identity;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "given_name")
    private String givenName;

    @Column(name = "identification_document", unique = true)
    private String identificationDocument;

    private String address;

    @Column(unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

}