package service_booking_api.core.domain.appointment.model;

public enum AppointmentStatus {

    CREATED("Scheduled"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled"),
    COMPLETED("Completed"),
    NO_SHOW("No Show");

    private final String displayName;

    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
