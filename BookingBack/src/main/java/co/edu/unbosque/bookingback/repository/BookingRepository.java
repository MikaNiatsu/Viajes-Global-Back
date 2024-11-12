package co.edu.unbosque.bookingback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.bookingback.model.BookingEntity;

public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

}
