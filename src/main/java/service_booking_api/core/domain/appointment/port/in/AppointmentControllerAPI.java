package service_booking_api.core.domain.appointment.port.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service_booking_api.core.domain.appointment.model.request.AppointmentRequest;
import service_booking_api.core.domain.appointment.model.response.AppointmentResponse;
import service_booking_api.shared.exceptions.ApplicationException;

import java.util.Set;


@RequestMapping("/appointments")
@Tag(name = "AppointmentController", description = "Collection for Appointments")
public interface AppointmentControllerAPI {

    @Operation(summary = "Create Appointment", tags = "AppointmentController")
    @PostMapping("/appointments")
    ResponseEntity<AppointmentResponse> createAppointment(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody AppointmentRequest appointment) throws ApplicationException;


}
