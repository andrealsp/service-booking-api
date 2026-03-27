package service_booking_api.core.domain.auth.entity;

public enum Role {
    CLIENT("Client"),
    PROVIDER("Provider"),
    ASSISTANT("Assistant"),
    ADMIN("Admin");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}

