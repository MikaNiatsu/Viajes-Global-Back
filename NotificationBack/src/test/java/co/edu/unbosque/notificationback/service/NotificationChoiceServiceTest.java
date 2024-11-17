package co.edu.unbosque.notificationback.service;

import co.edu.unbosque.notificationback.model.NotificationChoiceDTO;
import co.edu.unbosque.notificationback.model.NotificationChoiceEntity;
import co.edu.unbosque.notificationback.repository.NotificationChoiceRepository;
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

class NotificationChoiceServiceTest {

    @Mock
    private NotificationChoiceRepository notificationChoiceRepository;

    @InjectMocks
    private NotificationChoiceService notificationChoiceService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testFindByIdExisting() {
        NotificationChoiceEntity entity = new NotificationChoiceEntity(123, true, false, true);
        when(notificationChoiceRepository.findById(123)).thenReturn(Optional.of(entity));

        NotificationChoiceDTO result = notificationChoiceService.findById(123);

        assertEquals(123, result.getCustomer_id());
        assertTrue(result.isPush());
        assertFalse(result.isSms());
        assertTrue(result.isEmail());
        verify(notificationChoiceRepository, times(1)).findById(123);
    }

    @Test
    void testFindByIdNotExisting() {
        NotificationChoiceEntity newEntity = new NotificationChoiceEntity(123, false, false, false);
        when(notificationChoiceRepository.findById(123)).thenReturn(Optional.empty());
        when(notificationChoiceRepository.save(any(NotificationChoiceEntity.class))).thenReturn(newEntity);

        NotificationChoiceDTO result = notificationChoiceService.findById(123);

        assertEquals(123, result.getCustomer_id());
        assertFalse(result.isPush());
        assertFalse(result.isSms());
        assertFalse(result.isEmail());
        verify(notificationChoiceRepository, times(1)).findById(123);
        verify(notificationChoiceRepository, times(1)).save(any(NotificationChoiceEntity.class));
    }

    @Test
    void testFindAll() {
        NotificationChoiceEntity entity1 = new NotificationChoiceEntity(123, true, false, true);
        NotificationChoiceEntity entity2 = new NotificationChoiceEntity(124, false, true, false);
        when(notificationChoiceRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        List<NotificationChoiceDTO> result = notificationChoiceService.findAll();

        assertEquals(2, result.size());
        assertEquals(123, result.get(0).getCustomer_id());
        assertTrue(result.get(0).isPush());
        assertEquals(124, result.get(1).getCustomer_id());
        assertFalse(result.get(1).isPush());
        verify(notificationChoiceRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        NotificationChoiceDTO dto = new NotificationChoiceDTO(123, true, false, true);
        NotificationChoiceEntity entity = new NotificationChoiceEntity();
        entity.setCustomer_id(123);
        entity.setPush(true);
        entity.setSms(false);
        entity.setEmail(true);

        when(notificationChoiceRepository.save(any(NotificationChoiceEntity.class))).thenReturn(entity);

        NotificationChoiceDTO result = notificationChoiceService.save(dto);

        assertEquals(123, result.getCustomer_id());
        assertTrue(result.isPush());
        assertFalse(result.isSms());
        assertTrue(result.isEmail());
        verify(notificationChoiceRepository, times(1)).save(any(NotificationChoiceEntity.class));
    }


    @Test
    void testDeleteById() {
        doNothing().when(notificationChoiceRepository).deleteById(123);

        notificationChoiceService.deleteById(123);

        verify(notificationChoiceRepository, times(1)).deleteById(123);
    }

    @Test
    void testUpdateNotificationSuccess() {
        NotificationChoiceDTO dto = new NotificationChoiceDTO(123, true, true, false);
        NotificationChoiceEntity entity = DataMapper.notificationChoiceDTOtoEntity(dto);
        when(notificationChoiceRepository.save(any(NotificationChoiceEntity.class))).thenReturn(entity);

        boolean result = notificationChoiceService.updateNotification(dto);

        assertTrue(result);
        verify(notificationChoiceRepository, times(1)).save(any(NotificationChoiceEntity.class));
    }

    @Test
    void testUpdateNotificationFailure() {
        NotificationChoiceDTO dto = new NotificationChoiceDTO(123, true, true, false);
        NotificationChoiceEntity entity = DataMapper.notificationChoiceDTOtoEntity(dto);
        when(notificationChoiceRepository.save(any(NotificationChoiceEntity.class))).thenThrow(new RuntimeException("Error saving"));

        boolean result = notificationChoiceService.updateNotification(dto);

        assertFalse(result);
        verify(notificationChoiceRepository, times(1)).save(any(NotificationChoiceEntity.class));
    }
}
