package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.BookingDTO;
import co.edu.unbosque.bookingback.model.PackageEntity;
import co.edu.unbosque.bookingback.service.BookingService;
import co.edu.unbosque.bookingback.service.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Booking Controller", description = "Manage bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PackageService packageService;

    @Operation(summary = "Get booking by ID", description = "Retrieve a booking using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{bookingId}")
    public BookingDTO getBookingById(@PathVariable int bookingId) {
        return bookingService.findById(bookingId);
    }

    @Operation(summary = "Get bookings by customer ID", description = "Retrieve all bookings associated with a specific customer ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "404", description = "Customer or bookings not found")
    })
    @GetMapping("/getByCustomer/{customerId}")
    public List<BookingDTO> getBookingByCustomerId(@PathVariable int customerId) {
        return bookingService.findByCustomerId(customerId);
    }

    @Operation(summary = "Create a new booking", description = "Create a booking using the provided details in the request body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/createBooking")
    public ResponseEntity<Object> createBooking(@RequestBody Map<String, String> body) {
        int customer_id = Integer.parseInt(body.get("customer_id"));
        Instant instant = Instant.parse(body.get("booking_date"));
        Date booking_date = Date.from(instant);
        int package_id = Integer.parseInt(body.get("package_id"));
        String status = body.get("booking_status");
        PackageEntity packageEntity = packageService.findByIdEntity(package_id);
        String name = body.get("name");
        String email = body.get("email");
        String phone = body.get("phone");
        java.sql.Date booking_date_sql = new java.sql.Date(booking_date.getTime());
        BookingDTO bookingDTO = new BookingDTO(0, customer_id, booking_date_sql, status, name, email, phone, packageEntity);
        BookingDTO createdBooking = bookingService.save(bookingDTO);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a booking", description = "Delete a booking using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable int id) {
        bookingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all bookings", description = "Retrieve all bookings.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of bookings retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingDTO.class)))
    })
    @GetMapping("/showAll")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.findAll();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}