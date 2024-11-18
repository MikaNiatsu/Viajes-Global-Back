package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.BookingDTO;
import co.edu.unbosque.bookingback.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class BookingControllerTest {
    @Mock
    private BookingService bookingService;
    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }


    @Test
    void testGetBookingById() {
        int bookingId = 1;
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBooking_id(bookingId);

        when(bookingService.findById(bookingId)).thenReturn(bookingDTO);

        BookingDTO response = bookingController.getBookingById(bookingId);

        assertEquals(bookingDTO, response);
        verify(bookingService, times(1)).findById(bookingId);
    }



    @Test
    void testDeleteBooking() {
        int bookingId = 1;

        doNothing().when(bookingService).deleteById(bookingId);

        ResponseEntity<Void> response = bookingController.deleteBooking(bookingId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookingService, times(1)).deleteById(bookingId);
    }

    @Test
    void testGetAllBookings() {
        BookingDTO booking1 = new BookingDTO();
        booking1.setBooking_id(1);
        booking1.setName("Booking 1");

        BookingDTO booking2 = new BookingDTO();
        booking2.setBooking_id(2);
        booking2.setName("Booking 2");

        List<BookingDTO> bookings = Arrays.asList(booking1, booking2);

        when(bookingService.findAll()).thenReturn(bookings);

        ResponseEntity<List<BookingDTO>> response = bookingController.getAllBookings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookings, response.getBody());
        verify(bookingService, times(1)).findAll();
    }
}
