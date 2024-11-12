package co.edu.unbosque.bookingback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ActivityDTO {

    private int activity_id;
    private String name;
    private String description;
    private int price;
    private String location;
    private String category;
    private String images;
    private int rating;
    private int stock;


    public ActivityDTO() {
    }

    public ActivityDTO(int activity_id, String name, String description, int price, String location, String category, String images, int rating, int stock) {
        this.activity_id = activity_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.location = location;
        this.category = category;
        this.images = images;
        this.rating = rating;
        this.stock = stock;
    }
}