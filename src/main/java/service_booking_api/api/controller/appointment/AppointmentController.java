package service_booking_api.api.controller.appointment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import service_booking_api.core.domain.appointment.model.request.AppointmentRequest;
import service_booking_api.core.domain.appointment.model.response.AppointmentResponse;
import service_booking_api.core.domain.appointment.port.in.AppointmentControllerAPI;
import service_booking_api.shared.exceptions.ApplicationException;

import java.util.Set;

@Slf4j
@RestController
public class AppointmentController implements AppointmentControllerAPI {

    @Override
    public ResponseEntity<AppointmentResponse> createAppointments(AppointmentRequest appointment) throws ApplicationException {
        return null;
    }

    @Override
    public ResponseEntity<AppointmentResponse> retrieveAppointment(String id) throws ApplicationException {
        return null;
    }

    @Override
    public ResponseEntity<Set<AppointmentResponse>> retrieveProviderAppointments(String id) throws ApplicationException {
        return null;
    }

    @Override
    public ResponseEntity<AppointmentResponse> deleteAppointment(String id) throws ApplicationException {
        return null;
    }

    @Override
    public ResponseEntity<AppointmentResponse> cancelAppointment(String id) throws ApplicationException {
        return null;
    }
}
