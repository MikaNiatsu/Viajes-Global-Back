package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.ActivityEntity;
import co.edu.unbosque.bookingback.model.FlightEntity;
import co.edu.unbosque.bookingback.model.HotelEntity;
import co.edu.unbosque.bookingback.model.PackageDTO;
import co.edu.unbosque.bookingback.service.PackageService;
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

class PackageControllerTest {

    @Mock
    private PackageService packageService;

    @InjectMocks
    private PackageController packageController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }



    @Test
    void testGetPackageById() {
        int packageId = 1;
        PackageDTO packageDTO = createSamplePackageDTO();

        when(packageService.findById(packageId)).thenReturn(packageDTO);

        ResponseEntity<PackageDTO> response = packageController.getPackageById(packageId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(packageDTO, response.getBody());
        verify(packageService, times(1)).findById(packageId);
    }

    @Test
    void testDeletePackage() {
        int packageId = 1;

        doNothing().when(packageService).deleteById(packageId);

        ResponseEntity<Void> response = packageController.deletePackage(packageId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(packageService, times(1)).deleteById(packageId);
    }

    @Test
    void testGetAllPackages() {
        PackageDTO package1 = createSamplePackageDTO();
        PackageDTO package2 = createSamplePackageDTO();
        package2.setPackage_id(2);
        package2.setPrice(800);

        List<PackageDTO> packages = Arrays.asList(package1, package2);

        when(packageService.findAll()).thenReturn(packages);

        ResponseEntity<List<PackageDTO>> response = packageController.getAllPackages();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(packages, response.getBody());
        verify(packageService, times(1)).findAll();
    }

    private PackageDTO createSamplePackageDTO() {
        HotelEntity hotel = new HotelEntity();
        hotel.setHotel_id(1);
        hotel.setName("Hotel Plaza");

        FlightEntity flight = new FlightEntity();
        flight.setFlightId(1);
        flight.setAirline("Airways");
        flight.setDescription("3h");

        ActivityEntity activity = new ActivityEntity();
        activity.setActivity_id(1);
        activity.setName("City Tour");
        activity.setDescription("Explore the city landmarks");

        return new PackageDTO(1, 500, hotel, flight, activity);
    }
}
