package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.ActivityDTO;
import co.edu.unbosque.bookingback.model.ActivityEntity;
import co.edu.unbosque.bookingback.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        openMocks(this); // Inicializa los mocks
    }

    @Test
    void testFindAll() {
        ActivityEntity activity1 = new ActivityEntity();
        activity1.setActivity_id(1);
        activity1.setName("Sky");

        ActivityEntity activity2 = new ActivityEntity();
        activity2.setActivity_id(2);
        activity2.setName("Surf");

        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity1, activity2));

        List<ActivityDTO> activities = activityService.findAll();

        assertEquals(2, activities.size());
        assertEquals("Sky", activities.get(0).getName());
        assertEquals("Surf", activities.get(1).getName());
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setActivity_id(1);
        activityEntity.setName("Sky");

        when(activityRepository.findById(1)).thenReturn(Optional.of(activityEntity));

        ActivityDTO activityDTO = activityService.findById(1);

        assertEquals(1, activityDTO.getActivity_id());
        assertEquals("Sky", activityDTO.getName());
        verify(activityRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> activityService.findById(1));
        assertEquals("Activity not found with id: 1", exception.getMessage());
        verify(activityRepository, times(1)).findById(1);
    }


    @Test
    void testDeleteById() {
        doNothing().when(activityRepository).deleteById(1);

        activityService.deleteById(1);

        verify(activityRepository, times(1)).deleteById(1);
    }
}
