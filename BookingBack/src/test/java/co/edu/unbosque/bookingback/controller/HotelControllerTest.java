package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.HotelDTO;
import co.edu.unbosque.bookingback.service.HotelService;
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

class HotelControllerTest {
    @Mock
    private HotelService hotelService;
    @InjectMocks
    private HotelController hotelController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testCreateHotel() {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotel_id(1);
        hotelDTO.setName("Hotel 1");

        when(hotelService.save(hotelDTO)).thenReturn(hotelDTO);

        ResponseEntity<HotelDTO> response = hotelController.createHotel(hotelDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(hotelDTO, response.getBody());
        verify(hotelService, times(1)).save(hotelDTO);
    }

    @Test
    void testGetHotelById() {
        int hotelId = 1;
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotel_id(hotelId);
        hotelDTO.setName("Hotel 1");

        when(hotelService.findById(hotelId)).thenReturn(hotelDTO);

        ResponseEntity<HotelDTO> response = hotelController.getHotelById(hotelId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotelDTO, response.getBody());
        verify(hotelService, times(1)).findById(hotelId);
    }

    @Test
    void testDeleteHotel() {
        int hotelId = 1;

        doNothing().when(hotelService).deleteById(hotelId);

        ResponseEntity<Void> response = hotelController.deleteHotel(hotelId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(hotelService, times(1)).deleteById(hotelId);
    }

    @Test
    void testGetAllHotels() {
        HotelDTO hotel1 = new HotelDTO();
        hotel1.setHotel_id(1);
        hotel1.setName("Hotel 1");

        HotelDTO hotel2 = new HotelDTO();
        hotel2.setHotel_id(2);
        hotel2.setName("Hotel 2");

        List<HotelDTO> hotels = Arrays.asList(hotel1, hotel2);

        when(hotelService.findAll()).thenReturn(hotels);

        ResponseEntity<List<HotelDTO>> response = hotelController.getAllHotels();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hotels, response.getBody());
        verify(hotelService, times(1)).findAll();
    }
}
