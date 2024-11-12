package co.edu.unbosque.bookingback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.bookingback.model.FlightEntity;

public interface FlightRepository extends JpaRepository<FlightEntity, Integer> {

}
