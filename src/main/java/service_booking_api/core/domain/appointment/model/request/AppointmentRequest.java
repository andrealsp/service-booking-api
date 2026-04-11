package service_booking_api.core.domain.appointment.model.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import service_booking_api.core.domain.appointment.model.AppointmentStatus;
import service_booking_api.core.domain.user.model.UserDetails;
//import service_booking_api.shared.exceptions.ApplicationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequest {

    private UUID providerId;
    private UUID clientId;
    private String serviceName;

    @JsonAlias(value = "date")
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private String notes;

}
