package co.edu.unbosque.bookingback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter

public class FlightDTO {

    private Integer flightId;
    private String airline;
    private String origin;
    private String destination;
    private Timestamp departureDate;
    private Timestamp arrivalDate;
    private Double price;
    private String images;
    private String description;
    private Integer rating;
    private Integer stock;


    public FlightDTO() {
        // TODO Auto-generated constructor stub
    }

    public FlightDTO(Integer flightId, String airline, String origin, String destination, Timestamp departureDate, Timestamp arrivalDate, Double price, String images, String description, Integer rating, Integer stock) {
        this.flightId = flightId;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
        this.images = images;
        this.description = description;
        this.rating = rating;
        this.stock = stock;

    }
}