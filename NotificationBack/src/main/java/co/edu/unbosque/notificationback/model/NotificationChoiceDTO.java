package co.edu.unbosque.notificationback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NotificationChoiceDTO {

    private int customer_id;
    private boolean push;
    private boolean sms;
    private boolean email;

    public NotificationChoiceDTO() {

    }
    public NotificationChoiceDTO(int  customer_id, boolean push, boolean sms, boolean email) {
        this.customer_id = customer_id;
        this.push = push;
        this.sms = sms;
        this.email = email;
    }
}
