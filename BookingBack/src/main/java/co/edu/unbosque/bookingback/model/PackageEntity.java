package co.edu.unbosque.bookingback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="Tour_Packages")
@Getter
@Setter
public class PackageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int package_id;
	private int price;
	@ManyToOne
	@JoinColumn(name = "hotel_id", nullable = true)
	private HotelEntity hotelEntity;
	@ManyToOne
	@JoinColumn(name = "flight_id", nullable = true)
	private FlightEntity flightEntity;
	@ManyToOne
	@JoinColumn(name = "activity_id", nullable = true)
	private ActivityEntity activityEntity;


	public PackageEntity() {
	}

	public PackageEntity(int package_id, int price, HotelEntity hotelEntity, FlightEntity flightEntity, ActivityEntity activityEntity) {
		this.package_id = package_id;
		this.price = price;
		this.hotelEntity = hotelEntity;
		this.flightEntity = flightEntity;
		this.activityEntity = activityEntity;
	}
}