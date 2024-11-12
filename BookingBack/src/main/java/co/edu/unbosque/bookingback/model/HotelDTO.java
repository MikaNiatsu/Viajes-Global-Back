package co.edu.unbosque.bookingback.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class HotelDTO {


    private int hotel_id;
    private String name;
    private String address;
    private String city;
    private String country;
    private int price_per_night;
    private String images;
    private String description;
    private int rating;
    private int stock;


    public HotelDTO() {
        // TODO Auto-generated constructor stub
    }

    public HotelDTO(int hotel_id, String name, String address, String city, String country, int price_per_night, String images, String description, int rating, int stock) {
        this.hotel_id = hotel_id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.price_per_night = price_per_night;
        this.images = images;
        this.description = description;
        this.rating = rating;
        this.stock = stock;
    }
}
