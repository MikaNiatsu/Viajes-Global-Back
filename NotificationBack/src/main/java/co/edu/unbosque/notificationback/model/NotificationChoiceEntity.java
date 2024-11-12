package co.edu.unbosque.notificationback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Notification_preferences")
@Getter
@Setter
public class NotificationChoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;
    private boolean push;
    private boolean sms;
    private boolean email;

    public NotificationChoiceEntity() {

    }

    public NotificationChoiceEntity( int  customer_id, boolean push, boolean sms, boolean email) {
        this.customer_id = customer_id;
        this.push = push;
        this.sms = sms;
        this.email = email;
    }
}
