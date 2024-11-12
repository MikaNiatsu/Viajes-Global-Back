package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.HotelDTO;
import co.edu.unbosque.bookingback.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @PostMapping("/createHotel")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        HotelDTO createdHotel = hotelService.save(hotelDTO);
        return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable int id) {
        HotelDTO hotel = hotelService.findById(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable int id) {
        hotelService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/showAll")
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<HotelDTO> hotels = hotelService.findAll();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
}