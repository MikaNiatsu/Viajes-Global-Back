package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.*;
import co.edu.unbosque.bookingback.service.ActivityService;
import co.edu.unbosque.bookingback.service.FlightService;
import co.edu.unbosque.bookingback.service.HotelService;
import co.edu.unbosque.bookingback.service.PackageService;
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
import java.util.Map;


@RestController
@RequestMapping("/packages")
@Tag(name = "Package Controller", description = "Manage travel packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private HotelService hotelService;

    @Operation(summary = "Create a new package", description = "Create a travel package using provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Package created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping(value = "/createPackage")
    public ResponseEntity<Object> createPackage(@RequestBody Map<String, String> body) {
        int price = (int) Double.parseDouble(body.get("price"));
        Integer hotel_id = body.get("hotel_id") != null ? Integer.parseInt(body.get("hotel_id")) : null;
        Integer flight_id = body.get("flight_id") != null ? Integer.parseInt(body.get("flight_id")) : null;
        Integer activity_id = body.get("activity_id") != null ? Integer.parseInt(body.get("activity_id")) : null;
        HotelEntity hotel = hotel_id != null ? hotelService.findByIdEntity(hotel_id) : null;
        FlightEntity flight = flight_id != null ? flightService.findByIdEntity(flight_id) : null;
        ActivityEntity activity = activity_id != null ? activityService.findByIdEntity(activity_id) : null;
        int result = packageService.save(new PackageDTO(0, price, hotel, flight, activity));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Get package by ID", description = "Retrieve a travel package by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Package retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PackageDTO.class))),
            @ApiResponse(responseCode = "404", description = "Package not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PackageDTO> getPackageById(@PathVariable int id) {
        PackageDTO packageDTO = packageService.findById(id);
        return new ResponseEntity<>(packageDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete a package", description = "Delete a travel package by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Package deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Package not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable int id) {
        packageService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all packages", description = "Retrieve all available travel packages.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of packages retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PackageDTO.class)))
    })
    @GetMapping("/showAll")
    public ResponseEntity<List<PackageDTO>> getAllPackages() {
        List<PackageDTO> packages = packageService.findAll();
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }
}
