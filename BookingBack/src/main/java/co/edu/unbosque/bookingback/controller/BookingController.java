package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.ActivityDTO;
import co.edu.unbosque.bookingback.model.BookingDTO;
import co.edu.unbosque.bookingback.model.PackageDTO;
import co.edu.unbosque.bookingback.service.BookingService;
import co.edu.unbosque.bookingback.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{bookingId}")
    public BookingDTO getBookingById(@PathVariable int bookingId) {
       return bookingService.findById(bookingId);
    }
    @PostMapping("/createBooking")
    public ResponseEntity<BookingDTO> createBooking(BookingDTO bookingDTO) {
        BookingDTO createdBooking = bookingService.save(bookingDTO);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable int id) {
        bookingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/showAll")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.findAll();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }


}