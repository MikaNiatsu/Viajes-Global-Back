package co.edu.unbosque.bookingback.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PackageDTO {

    private int package_id;
    private int price;

    @JsonManagedReference
    private HotelEntity hotelEntity;
    @JsonManagedReference
    private FlightEntity flightEntity;
    @JsonManagedReference
    private ActivityEntity activityEntity;

    public PackageDTO() {
        // TODO Auto-generated constructor stub
    }

    public PackageDTO(int package_id, int price, HotelEntity hotelEntity, FlightEntity flightEntity, ActivityEntity activityEntity) {
        this.package_id = package_id;
        this.price = price;
        this.hotelEntity = hotelEntity;
        this.flightEntity = flightEntity;
        this.activityEntity = activityEntity;
    }
}
