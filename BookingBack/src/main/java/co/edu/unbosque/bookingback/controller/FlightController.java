package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.FlightDTO;
import co.edu.unbosque.bookingback.service.FlightService;
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
@RequestMapping("/flights")
@Tag(name = "Flight Controller", description = "Manage flight operations")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Operation(summary = "Create a new flight", description = "Create a new flight record using the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Flight created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlightDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid flight data provided")
    })
    @PostMapping("/createFlight")
    public ResponseEntity<FlightDTO> createFlight(@RequestBody FlightDTO flightDTO) {
        FlightDTO createdFlight = flightService.save(flightDTO);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    @Operation(summary = "Get flight by ID", description = "Retrieve a flight by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlightDTO.class))),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable int id) {
        FlightDTO flight = flightService.findById(id);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @Operation(summary = "Delete a flight", description = "Delete a flight by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Flight deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable int id) {
        flightService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all flights", description = "Retrieve all available flights.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flights retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))
    })
    @GetMapping("/showAll")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<FlightDTO> flights = flightService.findAll();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}