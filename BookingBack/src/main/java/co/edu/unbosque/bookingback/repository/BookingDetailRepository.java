package co.edu.unbosque.bookingback.repository;

import co.edu.unbosque.bookingback.model.BookingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailRepository extends JpaRepository<BookingDetailEntity,Integer> {
}
