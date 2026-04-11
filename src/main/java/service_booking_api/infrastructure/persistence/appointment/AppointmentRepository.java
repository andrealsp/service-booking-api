package service_booking_api.infrastructure.persistence.appointment;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import service_booking_api.core.domain.appointment.entity.AppointmentEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {

    @Query("SELECT i FROM AppointmentEntity i WHERE i.id = :id AND i.appointmentDate = :appointmentDate")
    List<AppointmentEntity> findByProviderIdAndDate(@Param("id") UUID id, @Param("appointmentDate") LocalDate appointmentDate);

}
