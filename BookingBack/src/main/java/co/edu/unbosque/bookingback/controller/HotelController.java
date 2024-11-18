package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.HotelDTO;
import co.edu.unbosque.bookingback.service.HotelService;
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

import java.util.List;

@RestController
@RequestMapping("/hotels")
@Tag(name = "Hotel Controller", description = "Manage hotel-related operations")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Operation(summary = "Create a new hotel", description = "Add a new hotel to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid hotel data provided")
    })
    @PostMapping("/createHotel")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        HotelDTO createdHotel = hotelService.save(hotelDTO);
        return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
    }

    @Operation(summary = "Get hotel by ID", description = "Retrieve details of a hotel by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HotelDTO.class))),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable int id) {
        HotelDTO hotel = hotelService.findById(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @Operation(summary = "Delete a hotel", description = "Remove a hotel by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hotel deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable int id) {
        hotelService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all hotels", description = "Retrieve a list of all hotels.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of hotels retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))
    })
    @GetMapping("/showAll")
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<HotelDTO> hotels = hotelService.findAll();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
}