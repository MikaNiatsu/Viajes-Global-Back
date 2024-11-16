package co.edu.unbosque.notificationback.controller;

import co.edu.unbosque.notificationback.model.NotificationChoiceDTO;
import co.edu.unbosque.notificationback.service.NotificationChoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class NotificationControllerTest {

    @Mock
    private NotificationChoiceService notificationChoiceService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testGetNotificationChoiceByClientId() {

        NotificationChoiceDTO notificationChoice = new NotificationChoiceDTO(123, true, false, true);

        when(notificationChoiceService.findById(123)).thenReturn(notificationChoice);

        NotificationChoiceDTO result = notificationController.getNotificationChoiceByClientId(123);

        assertEquals(123, result.getCustomer_id());
        assertTrue(result.isPush());
        assertFalse(result.isSms());
        assertTrue(result.isEmail());
        verify(notificationChoiceService, times(1)).findById(123);
    }

    @Test
    void testCreateNotificationChoice() {

        NotificationChoiceDTO notificationChoice = new NotificationChoiceDTO(123, true, false, true);

        when(notificationChoiceService.save(notificationChoice)).thenReturn(notificationChoice);


        ResponseEntity<NotificationChoiceDTO> response = notificationController.createNotificationChoice(notificationChoice);


        assertEquals(201, response.getStatusCodeValue());
        assertEquals(notificationChoice, response.getBody());
        verify(notificationChoiceService, times(1)).save(notificationChoice);
    }

    @Test
    void testUpdateNotification() {

        NotificationChoiceDTO notificationChoice = new NotificationChoiceDTO(123, false, true, false);

        when(notificationChoiceService.updateNotification(notificationChoice)).thenReturn(true);

        boolean result = notificationController.updateNotification(notificationChoice);

        assertTrue(result);
        verify(notificationChoiceService, times(1)).updateNotification(notificationChoice);
    }

    @Test
    void testGetNotificationChoiceById() {

        NotificationChoiceDTO notificationChoice = new NotificationChoiceDTO(123, true, true, false);

        when(notificationChoiceService.findById(123)).thenReturn(notificationChoice);

        ResponseEntity<NotificationChoiceDTO> response = notificationController.getNotificationChoiceById(123);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(notificationChoice, response.getBody());
        verify(notificationChoiceService, times(1)).findById(123);
    }

    @Test
    void testGetAllNotificationChoices() {
        NotificationChoiceDTO notificationChoice1 = new NotificationChoiceDTO(123, true, false, true);
        NotificationChoiceDTO notificationChoice2 = new NotificationChoiceDTO(124, false, true, false);

        when(notificationChoiceService.findAll()).thenReturn(Arrays.asList(notificationChoice1, notificationChoice2));

        ResponseEntity<List<NotificationChoiceDTO>> response = notificationController.getAllNotificationChoices();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().get(0).isPush());
        assertFalse(response.getBody().get(1).isPush());
        verify(notificationChoiceService, times(1)).findAll();
    }

    @Test
    void testDeleteNotificationChoice() {
        doNothing().when(notificationChoiceService).deleteById(123);

        ResponseEntity<Void> response = notificationController.deleteNotificationChoice(123);

        assertEquals(204, response.getStatusCodeValue());
        verify(notificationChoiceService, times(1)).deleteById(123);
    }
}
