package co.edu.unbosque.notificationback.repository;

import co.edu.unbosque.notificationback.model.NotificationChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationChoiceRepository extends JpaRepository<NotificationChoiceEntity,Integer> {


}
