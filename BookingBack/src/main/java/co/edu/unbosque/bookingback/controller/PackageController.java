package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.PackageDTO;
import co.edu.unbosque.bookingback.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping("/createPackage")
    public ResponseEntity<PackageDTO> createPackage(@RequestBody PackageDTO packageDTO) {
        PackageDTO createdPackage = packageService.save(packageDTO);
        return new ResponseEntity<>(createdPackage, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PackageDTO> getPackageById(@PathVariable int id) {
        PackageDTO packageDTO = packageService.findById(id);
        return new ResponseEntity<>(packageDTO, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable int id) {
        packageService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/showAll")
    public ResponseEntity<List<PackageDTO>> getAllPackages() {
        List<PackageDTO> packages = packageService.findAll();
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }
}
