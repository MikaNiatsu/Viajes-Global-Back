package co.edu.unbosque.bookingback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Flight_Booking_Details")
@Getter
@Setter
public class BookingDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booking_detail_id;
    private int flight_id;
    private int num_adults;
    private int num_children;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "booking_id")
    private BookingEntity bookingEntity;

    public BookingDetailEntity() {

    }

    public BookingDetailEntity(int booking_detail_id, int flight_id, int num_adults, int num_children, BookingEntity bookingEntity) {
        this.booking_detail_id = booking_detail_id;
        this.flight_id = flight_id;
        this.num_adults = num_adults;
        this.num_children = num_children;
        this.bookingEntity = bookingEntity;
    }
}
