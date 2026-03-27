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

@RequestMapping
@Tag(name = "AppointmentController", description = "Collection for Appointments")
public interface AppointmentControllerAPI {

    @Operation(summary = "Create Appointment", tags = "AppointmentController")
    @PostMapping("/appointments")
    ResponseEntity<AppointmentResponse> createAppointments(@Valid @RequestBody AppointmentRequest appointment) throws ApplicationException;

    @Operation(summary = "Retrieve Appointment by Id", tags = "AppointmentController")
    @GetMapping("/appointments/{id}")
    ResponseEntity<AppointmentResponse> retrieveAppointment(@PathVariable String id) throws ApplicationException;

    @Operation(summary = "Retrieve Provider Appointments", tags = "AppointmentController")
    @GetMapping("/provider/{id}/appointments")
    ResponseEntity<Set<AppointmentResponse>> retrieveProviderAppointments(@PathVariable String id) throws ApplicationException;

    @Operation(summary = "Delete Appointment by Id", tags = "AppointmentController")
    @DeleteMapping("/appointments/{id}")
    ResponseEntity<AppointmentResponse> deleteAppointment(@PathVariable String id) throws ApplicationException;


    @Operation(summary = "Cancel Appointment by Id", tags = "AppointmentController")
    @PatchMapping("/appointments/{id}/cancel")
    ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable String id) throws ApplicationException;

}
