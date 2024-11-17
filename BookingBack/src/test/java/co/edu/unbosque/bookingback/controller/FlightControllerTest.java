package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.FlightDTO;
import co.edu.unbosque.bookingback.service.FlightService;
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

class FlightControllerTest {
    @Mock
    private FlightService flightService;
    @InjectMocks
    private FlightController flightController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }


    @Test
    void testCreateFlight() {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightId(1);
        flightDTO.setAirline("VivaAreobus");

        when(flightService.save(flightDTO)).thenReturn(flightDTO);

        ResponseEntity<FlightDTO> response = flightController.createFlight(flightDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(flightDTO, response.getBody());
        verify(flightService, times(1)).save(flightDTO);
    }

    @Test
    void testGetFlightById() {
        int flightId = 1;
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightId(flightId);
        flightDTO.setAirline("VivaAereobus");

        when(flightService.findById(flightId)).thenReturn(flightDTO);

        ResponseEntity<FlightDTO> response = flightController.getFlightById(flightId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flightDTO, response.getBody());
        verify(flightService, times(1)).findById(flightId);
    }

    @Test
    void testDeleteFlight() {
        int flightId = 1;

        doNothing().when(flightService).deleteById(flightId);

        ResponseEntity<Void> response = flightController.deleteFlight(flightId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(flightService, times(1)).deleteById(flightId);
    }

    @Test
    void testGetAllFlights() {
        FlightDTO flight1 = new FlightDTO();
        flight1.setFlightId(1);
        flight1.setAirline("VivaAereobus");

        FlightDTO flight2 = new FlightDTO();
        flight2.setFlightId(2);
        flight2.setAirline("Avianca");

        List<FlightDTO> flights = Arrays.asList(flight1, flight2);

        when(flightService.findAll()).thenReturn(flights);

        ResponseEntity<List<FlightDTO>> response = flightController.getAllFlights();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flights, response.getBody());
        verify(flightService, times(1)).findAll();
    }
}
