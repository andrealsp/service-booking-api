package service_booking_api.core.application.service.appointment;

import org.springframework.stereotype.Service;
import service_booking_api.core.application.service.appointment.mapper.AppointmentEntityMapper;
import service_booking_api.core.application.service.appointment.mapper.AppointmentResponseMapper;
import service_booking_api.core.domain.appointment.model.TimeSlot;
import service_booking_api.core.domain.appointment.model.request.AppointmentRequest;
import service_booking_api.core.domain.appointment.model.response.AppointmentResponse;
import service_booking_api.core.domain.appointment.port.in.AppointmentPort;
import service_booking_api.core.domain.user.port.in.UserPort;
import service_booking_api.infrastructure.persistence.appointment.AppointmentRepository;
import service_booking_api.infrastructure.persistence.provider.ProviderAvailabilityRepository;
import service_booking_api.shared.exceptions.ApplicationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class AppointmentServiceManagement implements AppointmentPort {

    private final AppointmentRepository appointmentRepository;
    private final ProviderAvailabilityRepository availabilityRepository;
    private final UserPort userPort;

    public AppointmentServiceManagement(AppointmentRepository appointmentRepository,
                                        ProviderAvailabilityRepository availabilityRepository,
                                        UserPort userPort) {
        this.appointmentRepository = appointmentRepository;
        this.availabilityRepository = availabilityRepository;
        this.userPort = userPort;
    }

    @Override
    public AppointmentResponse createAppointment(String token, AppointmentRequest request) throws ApplicationException {

        validateAvailability(request.getProviderId(), request);

        var provider = userPort.retrieveUser(request.getProviderId());
        var client = userPort.retrieveUser(request.getClientId());

        var appointmentEntity = AppointmentEntityMapper.map(provider, client, request);

        return AppointmentResponseMapper.map(appointmentRepository.save(appointmentEntity));
    }

    // =========================
    // ✅ AVAILABILITY VALIDATION
    // =========================
    private void validateAvailability(UUID providerId, AppointmentRequest request) {

        validateBasicRules(request);

        int dayOfWeek = request.getAppointmentDate().getDayOfWeek().getValue();

        // 🚨 EXPLICIT WEEKEND VALIDATION
        if (dayOfWeek == 6 || dayOfWeek == 7) {
            throw new ApplicationException(400, "Provider does not work on weekends");
        }

        var availabilityList = availabilityRepository
                .findByProviderIdAndDayOfWeek(providerId, dayOfWeek);

        if (availabilityList.isEmpty()) {
            throw new ApplicationException(400, "Provider has no availability configured for this day");
        }

        boolean withinWorkingHours = availabilityList.stream()
                .anyMatch(slot -> isWithinTimeRange(
                        request.getStartTime(),
                        request.getEndTime(),
                        slot.getStartTime(),
                        slot.getEndTime()
                ));

        if (!withinWorkingHours) {
            throw new ApplicationException(400, "Selected time is outside provider working hours");
        }

        validateNoConflicts(request);
    }

    // =========================
    // 🧠 BASIC RULES
    // =========================
    private void validateBasicRules(AppointmentRequest request) {

        if (request.getAppointmentDate() == null) {
            throw new ApplicationException(400, "Appointment date is required");
        }

        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new ApplicationException(400, "Start and end time are required");
        }

        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new ApplicationException(400, "Invalid time range");
        }

        if (request.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new ApplicationException(400, "Cannot book in the past");
        }
    }

    // =========================
    // ⏱ TIME VALIDATION
    // =========================
    private boolean isWithinTimeRange(
            LocalTime start,
            LocalTime end,
            LocalTime availableStart,
            LocalTime availableEnd
    ) {
        return !start.isBefore(availableStart) && !end.isAfter(availableEnd);
    }

    // =========================
    // 🚫 CONFLICT VALIDATION
    // =========================
    private void validateNoConflicts(AppointmentRequest request) {

        var existingAppointments = appointmentRepository
                .findByProviderIdAndDate(
                        request.getProviderId(),
                        request.getAppointmentDate()
                );

        boolean hasConflict = existingAppointments.stream()
                .anyMatch(existing -> isOverlapping(
                        request.getStartTime(),
                        request.getEndTime(),
                        existing.getStartTime(),
                        existing.getEndTime()
                ));

        if (hasConflict) {
            throw new ApplicationException(409, "Time slot already booked");
        }
    }

    private boolean isOverlapping(
            LocalTime start1,
            LocalTime end1,
            LocalTime start2,
            LocalTime end2
    ) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
}
