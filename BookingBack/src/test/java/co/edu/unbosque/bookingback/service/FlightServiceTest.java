package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.FlightDTO;
import co.edu.unbosque.bookingback.model.FlightEntity;
import co.edu.unbosque.bookingback.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testFindAll() {
        FlightEntity flight1 = new FlightEntity();
        flight1.setFlightId(1);
        flight1.setAirline("Avianca");

        FlightEntity flight2 = new FlightEntity();
        flight2.setFlightId(2);
        flight2.setAirline("VivaAereobus");

        when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1, flight2));

        List<FlightDTO> flights = flightService.findAll();

        assertEquals(2, flights.size());
        assertEquals("Avianca", flights.get(0).getAirline());
        assertEquals("VivaAereobus", flights.get(1).getAirline());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setFlightId(1);
        flightEntity.setAirline("Avianca");

        when(flightRepository.findById(1)).thenReturn(Optional.of(flightEntity));

        FlightDTO flightDTO = flightService.findById(1);

        assertEquals(1, flightDTO.getFlightId());
        assertEquals("Avianca", flightDTO.getAirline());
        verify(flightRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        // Configuración
        when(flightRepository.findById(1)).thenReturn(Optional.empty());

        // Ejecución y Verificación
        Exception exception = assertThrows(RuntimeException.class, () -> flightService.findById(1));
        assertEquals("Flight not found with id: 1", exception.getMessage());
        verify(flightRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        // Setup - Create a complete FlightDTO with all required fields
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightId(1);
        flightDTO.setAirline("Avianca");
        flightDTO.setOrigin("Bogota");
        flightDTO.setDestination("Medellin");
        flightDTO.setDepartureDate(Timestamp.valueOf("2024-11-16 10:00:00"));
        flightDTO.setArrivalDate(Timestamp.valueOf("2024-11-16 11:30:00"));
        flightDTO.setPrice(250000.0);
        flightDTO.setImages("flight-image-url.jpg");
        flightDTO.setDescription("Direct flight from Bogota to Medellin");
        flightDTO.setRating(5);
        flightDTO.setStock(100);

        // Create the entity that should be returned by the repository
        FlightEntity savedEntity = new FlightEntity();
        savedEntity.setFlightId(1);
        savedEntity.setAirline("Avianca");
        savedEntity.setOrigin("Bogota");
        savedEntity.setDestination("Medellin");
        savedEntity.setDepartureDate(Timestamp.valueOf("2024-11-16 10:00:00"));
        savedEntity.setArrivalDate(Timestamp.valueOf("2024-11-16 11:30:00"));
        savedEntity.setPrice(250000.0);
        savedEntity.setImages("flight-image-url.jpg");
        savedEntity.setDescription("Direct flight from Bogota to Medellin");
        savedEntity.setRating(5);
        savedEntity.setStock(100);

        when(flightRepository.save(any(FlightEntity.class))).thenReturn(savedEntity);

        FlightDTO savedFlightDTO = flightService.save(flightDTO);

        assertNotNull(savedFlightDTO);
        assertEquals(flightDTO.getFlightId(), savedFlightDTO.getFlightId());
        assertEquals(flightDTO.getAirline(), savedFlightDTO.getAirline());
        assertEquals(flightDTO.getOrigin(), savedFlightDTO.getOrigin());
        assertEquals(flightDTO.getDestination(), savedFlightDTO.getDestination());
        assertEquals(flightDTO.getDepartureDate(), savedFlightDTO.getDepartureDate());
        assertEquals(flightDTO.getArrivalDate(), savedFlightDTO.getArrivalDate());
        assertEquals(flightDTO.getPrice(), savedFlightDTO.getPrice());
        assertEquals(flightDTO.getImages(), savedFlightDTO.getImages());
        assertEquals(flightDTO.getDescription(), savedFlightDTO.getDescription());
        assertEquals(flightDTO.getRating(), savedFlightDTO.getRating());
        assertEquals(flightDTO.getStock(), savedFlightDTO.getStock());

        verify(flightRepository, times(1)).save(any(FlightEntity.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(flightRepository).deleteById(1);

        flightService.deleteById(1);

        verify(flightRepository, times(1)).deleteById(1);
    }
}
