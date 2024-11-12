package co.edu.unbosque.notificationback.service;

import co.edu.unbosque.notificationback.model.NotificationChoiceDTO;
import co.edu.unbosque.notificationback.model.NotificationChoiceEntity;
import co.edu.unbosque.notificationback.repository.NotificationChoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationChoiceService {

    @Autowired
    private NotificationChoiceRepository notificationChoiceRepository;

    public NotificationChoiceService() {

    }
   public NotificationChoiceDTO findById(int id){
        NotificationChoiceEntity entity = notificationChoiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("NotificationChoice not found for id: " + id));;
        return DataMapper.notificationChoiceEntityToDTO(entity);
    }
    public List<NotificationChoiceDTO> findAll() {
        return notificationChoiceRepository.findAll().stream()
                .map(DataMapper::notificationChoiceEntityToDTO)
                .collect(Collectors.toList());
    }

    public NotificationChoiceDTO save(NotificationChoiceDTO notificationChoiceDTO) {
        NotificationChoiceEntity notificationChoice = DataMapper.notificationChoiceDTOtoEntity(notificationChoiceDTO);
        NotificationChoiceEntity savedNotificationChoice = notificationChoiceRepository.save(notificationChoice);
        return DataMapper.notificationChoiceEntityToDTO(savedNotificationChoice);
    }

    public void deleteById(int id) {
        notificationChoiceRepository.deleteById(id);
    }

}
