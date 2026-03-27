package service_booking_api.core.domain.appointment.model.request;

import lombok.Getter;
import lombok.Setter;
import service_booking_api.core.domain.appointment.model.AppointmentStatus;
//import service_booking_api.shared.exceptions.ApplicationException;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequest {

    private UUID id;
    private UUID providerId;
    private UUID clientId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private AppointmentStatus status;

//    public void cancel() throws ApplicationException {
//        if (status == AppointmentStatus.CANCELLED) {
//            throw new ApplicationException(400,"Appointment already cancelled");
//        }
//
//        this.status = AppointmentStatus.CANCELLED;
//    }

}
