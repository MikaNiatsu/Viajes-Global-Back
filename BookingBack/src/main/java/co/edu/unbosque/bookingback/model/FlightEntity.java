package co.edu.unbosque.bookingback.model;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Flights")
@Getter
@Setter
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id", unique = true, nullable = false)
    private Integer flightId;
    @Column(name = "airline", nullable = false, length = 100)
    private String airline;
    @Column(name = "origin", nullable = false, length = 100)
    private String origin;
    @Column(name = "destination", nullable = false, length = 100)
    private String destination;
    @Column(name = "departure_date", nullable = false)
    private Timestamp departureDate;
    @Column(name = "arrival_date", nullable = false)
    private Timestamp arrivalDate;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "images")
    private String images;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "stock", nullable = false)
    private Integer stock;

    public FlightEntity() {

    }

    public FlightEntity(Integer flightId, String airline, String origin, String destination, Timestamp departureDate, Timestamp arrivalDate, Double price, String images, String description, Integer rating, Integer stock) {
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