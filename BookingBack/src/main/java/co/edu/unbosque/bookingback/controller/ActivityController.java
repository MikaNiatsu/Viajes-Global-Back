package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.ActivityDTO;
import co.edu.unbosque.bookingback.service.ActivityService;
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
@RequestMapping("/activities")
@Tag(name = "Activity Controller", description = "Manage activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "Create a new activity", description = "Creates a new activity and returns the created object.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Activity created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/createActivity")
    public ResponseEntity<ActivityDTO> createActivity(@RequestBody ActivityDTO activityDTO) {
        ActivityDTO createdActivity = activityService.save(activityDTO);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @Operation(summary = "Get an activity by ID", description = "Fetch an activity using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityDTO.class))),
            @ApiResponse(responseCode = "404", description = "Activity not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable int id) {
        ActivityDTO activity = activityService.findById(id);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @Operation(summary = "Delete an activity", description = "Deletes an activity by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Activity not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable int id) {
        activityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all activities", description = "Fetches all activities.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of activities",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityDTO.class)))
    })
    @GetMapping("/showAll")
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<ActivityDTO> activities = activityService.findAll();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
}
