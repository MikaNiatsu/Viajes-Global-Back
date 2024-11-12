package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.ActivityDTO;
import co.edu.unbosque.bookingback.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @PostMapping("/createActivity")
    public ResponseEntity<ActivityDTO> createActivity(@RequestBody ActivityDTO activityDTO) {
        ActivityDTO createdActivity = activityService.save(activityDTO);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable int id) {
        ActivityDTO activity = activityService.findById(id);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable int id) {
        activityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/showAll")
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<ActivityDTO> activities = activityService.findAll();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
}
