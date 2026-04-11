package service_booking_api.core.domain.appointment.model.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class AppointmentResponse {

    private UUID id;
    private String providerName;
    private String serviceName;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private String status;

}