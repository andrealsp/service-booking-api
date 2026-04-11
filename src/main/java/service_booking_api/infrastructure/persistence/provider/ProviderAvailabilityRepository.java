package service_booking_api.infrastructure.persistence.provider;

import org.springframework.data.jpa.repository.JpaRepository;
import service_booking_api.core.domain.provider.entity.ProviderAvailabilityEntity;

import java.util.List;
import java.util.UUID;

public interface ProviderAvailabilityRepository extends JpaRepository<ProviderAvailabilityEntity, UUID> {

    List<ProviderAvailabilityEntity> findByProviderId(UUID providerId);

    List<ProviderAvailabilityEntity> findByProviderIdAndDayOfWeek(UUID providerId, int dayOfWeek);


    
}
