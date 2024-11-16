package co.edu.unbosque.bookingback.controller;

import co.edu.unbosque.bookingback.model.ActivityDTO;
import co.edu.unbosque.bookingback.service.ActivityService;
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

class ActivityControllerTest {

    @Mock
    private ActivityService activityService;
    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }


    @Test
    void testCreateActivity() {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setActivity_id(1);
        activityDTO.setName("Surf");

        when(activityService.save(activityDTO)).thenReturn(activityDTO);

        ResponseEntity<ActivityDTO> response = activityController.createActivity(activityDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(activityDTO, response.getBody());
        verify(activityService, times(1)).save(activityDTO);
    }

    @Test
    void testGetActivityById() {
        int id = 1;
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setActivity_id(id);
        activityDTO.setName("Surf");

        when(activityService.findById(id)).thenReturn(activityDTO);

        ResponseEntity<ActivityDTO> response = activityController.getActivityById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activityDTO, response.getBody());
        verify(activityService, times(1)).findById(id);
    }

    @Test
    void testDeleteActivity() {
        int id = 1;

        doNothing().when(activityService).deleteById(id);

        ResponseEntity<Void> response = activityController.deleteActivity(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(activityService, times(1)).deleteById(id);
    }

    @Test
    void testGetAllActivities() {
        ActivityDTO activity1 = new ActivityDTO();
        activity1.setActivity_id(1);
        activity1.setName("Surf");

        ActivityDTO activity2 = new ActivityDTO();
        activity2.setActivity_id(2);
        activity2.setName("Sky");

        List<ActivityDTO> activities = Arrays.asList(activity1, activity2);

        when(activityService.findAll()).thenReturn(activities);

        ResponseEntity<List<ActivityDTO>> response = activityController.getAllActivities();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activities, response.getBody());
        verify(activityService, times(1)).findAll();
    }
}