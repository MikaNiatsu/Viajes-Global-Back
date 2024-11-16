package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.FlightDTO;
import co.edu.unbosque.bookingback.model.FlightEntity;
import co.edu.unbosque.bookingback.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightId(1);
        flightDTO.setAirline("Avianca");

        FlightEntity flightEntity = DataMapper.flightDTOtoEntity(flightDTO);
        FlightEntity savedEntity = DataMapper.flightDTOtoEntity(flightDTO);

        when(flightRepository.save(flightEntity)).thenReturn(savedEntity);

        FlightDTO savedFlightDTO = flightService.save(flightDTO);

        assertEquals(flightDTO.getFlightId(), savedFlightDTO.getFlightId());
        assertEquals(flightDTO.getAirline(), savedFlightDTO.getAirline());
        verify(flightRepository, times(1)).save(flightEntity);
    }

    @Test
    void testDeleteById() {
        doNothing().when(flightRepository).deleteById(1);

        flightService.deleteById(1);

        verify(flightRepository, times(1)).deleteById(1);
    }
}
