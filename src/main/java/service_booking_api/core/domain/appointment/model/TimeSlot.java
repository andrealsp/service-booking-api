package service_booking_api.core.domain.appointment.model;

import java.time.LocalTime;

public class TimeSlot {

    private LocalTime start;
    private LocalTime end;

    public TimeSlot(LocalTime startTime, LocalTime endTime) {
        this.start = startTime;
        this.end = endTime;
    }

    public boolean overlaps(TimeSlot other) {
        return !(end.isBefore(other.start) || start.isAfter(other.end));
    }
}
