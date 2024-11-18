package co.edu.unbosque.bookingback.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.bookingback.model.BookingEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    @Query("select b from BookingEntity b where b.customer_id = :customer_id")
    List<BookingEntity> findByCustomer_id(@Param("customer_id") int customer_id);
}
