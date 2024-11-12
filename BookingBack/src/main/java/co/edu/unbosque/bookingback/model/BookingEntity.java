package co.edu.unbosque.bookingback.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Bookings")
@Getter
@Setter
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booking_id;
    private int customer_id;
    private Date booking_date;
    private String booking_status;
    private String name;
    private String email;
    private String phone;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "package_id", referencedColumnName = "package_id")
    private PackageEntity packageEntity;




    public BookingEntity() {

    }

    public BookingEntity(int booking_id, int customer_id, Date booking_date, String booking_status, String name, String email, String phone, PackageEntity packageEntity) {
        this.booking_id = booking_id;
        this.customer_id = customer_id;
        this.booking_date = booking_date;
        this.booking_status = booking_status;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.packageEntity = packageEntity;

    }
}