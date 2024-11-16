package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.HotelDTO;
import co.edu.unbosque.bookingback.model.HotelEntity;
import co.edu.unbosque.bookingback.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testFindAll() {
        HotelEntity hotel1 = new HotelEntity();
        hotel1.setHotel_id(1);
        hotel1.setName("Hotel 1");

        HotelEntity hotel2 = new HotelEntity();
        hotel2.setHotel_id(2);
        hotel2.setName("Hotel 2");

        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel1, hotel2));

        List<HotelDTO> hotels = hotelService.findAll();

        assertEquals(2, hotels.size());
        assertEquals("Hotel 1", hotels.get(0).getName());
        assertEquals("Hotel 2", hotels.get(1).getName());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setHotel_id(1);
        hotelEntity.setName("Hotel 1");

        when(hotelRepository.findById(1)).thenReturn(Optional.of(hotelEntity));

        HotelDTO hotelDTO = hotelService.findById(1);

        assertEquals(1, hotelDTO.getHotel_id());
        assertEquals("Hotel 1", hotelDTO.getName());
        verify(hotelRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(hotelRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> hotelService.findById(1));
        assertEquals("Hotel not found with id: 1", exception.getMessage());
        verify(hotelRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotel_id(1);
        hotelDTO.setName("Hotel 1");

        HotelEntity hotelEntity = DataMapper.hotelDTOtoEntity(hotelDTO);
        HotelEntity savedEntity = DataMapper.hotelDTOtoEntity(hotelDTO);

        when(hotelRepository.save(hotelEntity)).thenReturn(savedEntity);

        HotelDTO savedHotelDTO = hotelService.save(hotelDTO);

        assertEquals(hotelDTO.getHotel_id(), savedHotelDTO.getHotel_id());
        assertEquals(hotelDTO.getName(), savedHotelDTO.getName());
        verify(hotelRepository, times(1)).save(hotelEntity);
    }

    @Test
    void testDeleteById() {
        doNothing().when(hotelRepository).deleteById(1);

        hotelService.deleteById(1);

        verify(hotelRepository, times(1)).deleteById(1);
    }
}
