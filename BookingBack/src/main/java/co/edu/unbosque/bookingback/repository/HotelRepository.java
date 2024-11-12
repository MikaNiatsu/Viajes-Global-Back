package co.edu.unbosque.bookingback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.bookingback.model.HotelEntity;

public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {

}
