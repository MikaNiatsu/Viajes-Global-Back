package co.edu.unbosque.bookingback.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ClientDTO {

    private int customer_id;
    private String email;
    private String user;
    private String phone;
    private String password;
    private Set<Long> bookingIds;
    private Long notificationChoiceId;
    public ClientDTO() {

    }

    public ClientDTO(int customer_id, String email, String user, String phone, String password, Set<Long> bookingIds, Long notificationChoiceId) {
        this.customer_id = customer_id;
        this.email = email;
        this.user = user;
        this.phone = phone;
        this.password = password;
        this.bookingIds = bookingIds;
        this.notificationChoiceId = notificationChoiceId;
    }
}
