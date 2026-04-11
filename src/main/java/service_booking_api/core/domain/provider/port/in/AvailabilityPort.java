package service_booking_api.core.domain.provider.port.in;

import service_booking_api.core.domain.provider.entity.ProviderAvailabilityEntity;

import java.util.List;
import java.util.UUID;

public interface AvailabilityPort {

    List<ProviderAvailabilityEntity> findByProvider(UUID providerId);

}
