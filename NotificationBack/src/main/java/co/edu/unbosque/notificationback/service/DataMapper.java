package co.edu.unbosque.notificationback.service;

import co.edu.unbosque.notificationback.model.NotificationChoiceDTO;
import co.edu.unbosque.notificationback.model.NotificationChoiceEntity;

public class DataMapper {
    public static NotificationChoiceDTO notificationChoiceEntityToDTO(NotificationChoiceEntity entity) {
        return new NotificationChoiceDTO(
                entity.getCustomer_id(),
                entity.isPush(),
                entity.isSms(),
                entity.isEmail()
        );
    }

    public static NotificationChoiceEntity notificationChoiceDTOtoEntity(NotificationChoiceDTO dto) {
        return new NotificationChoiceEntity(
                dto.getCustomer_id(),
                dto.isPush(),
                dto.isSms(),
                dto.isEmail()
        );
    }
}
