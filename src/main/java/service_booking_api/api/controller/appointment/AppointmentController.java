package service_booking_api.api.controller.appointment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service_booking_api.core.domain.appointment.model.request.AppointmentRequest;
import service_booking_api.core.domain.appointment.model.response.AppointmentResponse;
import service_booking_api.core.domain.appointment.port.in.AppointmentControllerAPI;
import service_booking_api.core.domain.appointment.port.in.AppointmentPort;
import service_booking_api.core.domain.auth.port.in.AuthPort;
import service_booking_api.shared.exceptions.ApplicationException;

import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
public class AppointmentController implements AppointmentControllerAPI {

    private final AppointmentPort appointmentService;

    public AppointmentController(AppointmentPort appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(String token, AppointmentRequest request) throws ApplicationException {
        return ResponseEntity.ok(appointmentService.createAppointment(token, request));
    }

}
