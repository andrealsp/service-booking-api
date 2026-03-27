package service_booking_api.api.controller.appointment;

import org.springframework.http.ResponseEntity;
import service_booking_api.core.domain.appointment.port.in.AppointmentControllerAPI;
import service_booking_api.core.domain.user.model.request.UserSigninRequest;
import service_booking_api.core.domain.user.model.request.UserSignupRequest;
import service_booking_api.shared.exceptions.ApplicationException;

public class AppointmentController implements AppointmentControllerAPI {
    @Override
    public ResponseEntity<?> createAppointments(UserSignupRequest request) throws ApplicationException {
        return null;
    }

    @Override
    public ResponseEntity<?> confirmAppointment(UserSigninRequest request) throws ApplicationException {
        return null;
    }

    @Override
    public ResponseEntity<?> retrieveProviderAppointments(String token) throws ApplicationException {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteAppointment(String token) throws ApplicationException {
        return null;
    }

    @Override
    public ResponseEntity<?> cancelAppointment(String token) throws ApplicationException {
        return null;
    }
}
