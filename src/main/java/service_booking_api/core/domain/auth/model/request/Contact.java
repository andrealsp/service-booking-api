package service_booking_api.core.domain.auth.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {

    private String phoneNumber;
    private String email;
    private String address;

}
