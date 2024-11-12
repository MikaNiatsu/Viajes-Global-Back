package co.edu.unbosque.bookingback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter


public class BookingDTO {

    private int booking_id;
    private int customer_id;
    private Date booking_date;
    private String booking_status;
    private String name;
    private String email;
    private String phone;
    @JsonManagedReference
    private PackageEntity packageEntity;


    public BookingDTO() {
        // TODO Auto-generated constructor stub
    }

    public BookingDTO(int booking_id, int customer_id,  Date booking_date, String booking_status, String name, String email, String phone, PackageEntity packageEntity) {
        this.booking_id = booking_id;
        this.customer_id = customer_id;
        this.booking_date = booking_date;
        this.booking_status = booking_status;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.packageEntity = packageEntity;
    }

    public BookingDTO(BookingDTO bookingDTO) {
    }
}