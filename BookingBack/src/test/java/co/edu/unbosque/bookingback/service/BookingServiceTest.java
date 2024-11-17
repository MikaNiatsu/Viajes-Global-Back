package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.BookingDTO;
import co.edu.unbosque.bookingback.model.BookingEntity;
import co.edu.unbosque.bookingback.model.PackageEntity;
import co.edu.unbosque.bookingback.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testFindAll() {
        PackageEntity packageEntity = new PackageEntity();
        BookingEntity booking1 = new BookingEntity();
        booking1.setBooking_id(1);
        booking1.setCustomer_id(100);
        booking1.setBooking_date(Date.valueOf("2023-11-16"));
        booking1.setBooking_status("CONFIRMED");
        booking1.setPackageEntity(packageEntity);

        BookingEntity booking2 = new BookingEntity();
        booking2.setBooking_id(2);
        booking2.setCustomer_id(101);
        booking2.setBooking_date(Date.valueOf("2023-11-17"));
        booking2.setBooking_status("PENDING");
        booking2.setPackageEntity(packageEntity);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        List<BookingDTO> bookings = bookingService.findAll();

        assertEquals(2, bookings.size());
        assertNull(bookings.get(0).getName());
        assertNull(bookings.get(1).getName());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        PackageEntity packageEntity = new PackageEntity();
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setBooking_id(1);
        bookingEntity.setCustomer_id(100);
        bookingEntity.setBooking_date(Date.valueOf("2023-11-16"));
        bookingEntity.setBooking_status("CONFIRMED");
        bookingEntity.setPackageEntity(packageEntity);

        when(bookingRepository.findById(1)).thenReturn(Optional.of(bookingEntity));

        BookingDTO bookingDTO = bookingService.findById(1);

        assertEquals(1, bookingDTO.getBooking_id());
        assertNull(bookingDTO.getName());
        assertNull(bookingDTO.getEmail());
        verify(bookingRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> bookingService.findById(1));
        assertEquals("Booking not found for id: 1", exception.getMessage());
        verify(bookingRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        // Setup - Create a complete BookingDTO with required fields
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBooking_id(1);
        bookingDTO.setCustomer_id(100);
        bookingDTO.setBooking_date(Date.valueOf("2023-11-16"));
        bookingDTO.setBooking_status("PENDING");
        bookingDTO.setName("John Doe");
        bookingDTO.setEmail("john@example.com");
        bookingDTO.setPhone("1234567890");

        // Create the entity that should be returned by the repository
        BookingEntity savedEntity = new BookingEntity();
        savedEntity.setBooking_id(1);
        savedEntity.setCustomer_id(100);
        savedEntity.setBooking_date(Date.valueOf("2023-11-16"));
        savedEntity.setBooking_status("PENDING");
        savedEntity.setName("John Doe");
        savedEntity.setEmail("john@example.com");
        savedEntity.setPhone("1234567890");

        // Mock repository behavior
        when(bookingRepository.save(any(BookingEntity.class))).thenReturn(savedEntity);

        // Execute
        BookingDTO savedBookingDTO = bookingService.save(bookingDTO);

        // Verify
        assertNotNull(savedBookingDTO);
        assertEquals(bookingDTO.getBooking_id(), savedBookingDTO.getBooking_id());
        assertEquals(bookingDTO.getCustomer_id(), savedBookingDTO.getCustomer_id());
        assertEquals(bookingDTO.getBooking_date(), savedBookingDTO.getBooking_date());
        assertEquals(bookingDTO.getBooking_status(), savedBookingDTO.getBooking_status());
        assertEquals(bookingDTO.getName(), savedBookingDTO.getName());
        assertEquals(bookingDTO.getEmail(), savedBookingDTO.getEmail());
        assertEquals(bookingDTO.getPhone(), savedBookingDTO.getPhone());

        verify(bookingRepository, times(1)).save(any(BookingEntity.class));
    }

    @Test
    void testSaveNull() {
        BookingDTO bookingDTO = null;

        Exception exception = assertThrows(NullPointerException.class, () -> bookingService.save(bookingDTO));
        assertEquals("Cannot invoke \"co.edu.unbosque.bookingback.model.BookingDTO.getBooking_id()\" because \"dto\" is null", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        doNothing().when(bookingRepository).deleteById(1);

        bookingService.deleteById(1);

        verify(bookingRepository, times(1)).deleteById(1);
    }
}
