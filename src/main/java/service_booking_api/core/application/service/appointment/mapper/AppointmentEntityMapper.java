package service_booking_api.core.application.service.appointment.mapper;

import org.springframework.stereotype.Component;
import service_booking_api.core.domain.appointment.entity.AppointmentEntity;
import service_booking_api.core.domain.appointment.model.AppointmentStatus;
import service_booking_api.core.domain.appointment.model.request.AppointmentRequest;
import service_booking_api.core.domain.user.entity.UserEntity;

import java.time.OffsetDateTime;

@Component
public class AppointmentEntityMapper {

    public static AppointmentEntity map(UserEntity provider, UserEntity client, AppointmentRequest request) {
        return AppointmentEntity.builder()
                .user(client)
                .provider(provider)
                .serviceName(request.getServiceName())
                .appointmentDate(request.getAppointmentDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(AppointmentStatus.CREATED)
                .notes(request.getNotes())
                .createdAt(OffsetDateTime.now())
                .build();
    }

}