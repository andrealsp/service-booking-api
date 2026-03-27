package service_booking_api.core.domain.appointment.port.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service_booking_api.core.domain.auth.model.AuthToken;
import service_booking_api.core.domain.user.model.request.UserSigninRequest;
import service_booking_api.core.domain.user.model.request.UserSignupRequest;
import service_booking_api.shared.exceptions.ApplicationException;

@RequestMapping
@Tag(name = "AppointmentController", description = "Collection for Appointments")
public interface AppointmentControllerAPI {

    @Operation(summary = "", tags = "AppointmentController")
    @PostMapping("/appointments")
    ResponseEntity<?> createAppointments(@Valid @RequestBody UserSignupRequest request) throws ApplicationException;

    @Operation(summary = "", tags = "AppointmentController")
    @GetMapping("/appointments/{id}")
    ResponseEntity<?> confirmAppointment(@Valid @RequestBody UserSigninRequest request) throws ApplicationException;

    @Operation(summary = "", tags = "AppointmentController")
    @GetMapping("/provider/{id}/appointments")
    ResponseEntity<?> retrieveProviderAppointments(@RequestHeader(value = "Authorization") String token) throws ApplicationException;

    @Operation(summary = "", tags = "AppointmentController")
    @DeleteMapping("/appointments/{id}")
    ResponseEntity<?> deleteAppointment(@RequestHeader(value = "Authorization") String token) throws ApplicationException;


    @Operation(summary = "", tags = "AppointmentController")
    @PatchMapping("/appointments/{id}/cancel")
    ResponseEntity<?> cancelAppointment(@RequestHeader(value = "Authorization") String token) throws ApplicationException;


}
