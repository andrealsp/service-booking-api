package service_booking_api.core.domain.appointment.port.in;

import service_booking_api.core.domain.appointment.model.request.AppointmentRequest;
import service_booking_api.core.domain.appointment.model.response.AppointmentResponse;
import service_booking_api.shared.exceptions.ApplicationException;

public interface AppointmentPort {

    AppointmentResponse createAppointment(String token, AppointmentRequest request) throws ApplicationException;

}
