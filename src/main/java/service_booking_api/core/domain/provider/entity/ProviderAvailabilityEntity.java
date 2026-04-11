package service_booking_api.core.domain.provider.entity;

import jakarta.persistence.*;
import lombok.Getter;
import service_booking_api.core.domain.user.entity.UserEntity;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "provider_availability")
public class ProviderAvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private UserEntity provider;

    private Integer dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;
}
