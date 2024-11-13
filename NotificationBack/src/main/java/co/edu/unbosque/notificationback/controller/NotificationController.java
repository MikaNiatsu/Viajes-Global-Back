package co.edu.unbosque.notificationback.controller;

import co.edu.unbosque.notificationback.model.NotificationChoiceDTO;
import co.edu.unbosque.notificationback.service.NotificationChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    public NotificationChoiceService notificationChoiceService;

    @GetMapping("/client/{clientId}")
    public NotificationChoiceDTO getNotificationChoiceByClientId(@PathVariable int clientId) {
        return notificationChoiceService.findById(clientId);
    }
    @PostMapping("/createNotification")
    public ResponseEntity<NotificationChoiceDTO> createNotificationChoice(@RequestBody NotificationChoiceDTO notificationChoiceDTO) {
        NotificationChoiceDTO createdNotificationChoice = notificationChoiceService.save(notificationChoiceDTO);
        return new ResponseEntity<>(createdNotificationChoice, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public boolean updateNotification(@RequestBody NotificationChoiceDTO notificationChoiceDTO){
        return notificationChoiceService.updateNotification(notificationChoiceDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<NotificationChoiceDTO> getNotificationChoiceById(@PathVariable int id) {
        NotificationChoiceDTO notificationChoice = notificationChoiceService.findById(id);
        return new ResponseEntity<>(notificationChoice, HttpStatus.OK);
    }

    @GetMapping("showAll")
    public ResponseEntity<List<NotificationChoiceDTO>> getAllNotificationChoices() {
        List<NotificationChoiceDTO> notificationChoices = notificationChoiceService.findAll();
        return new ResponseEntity<>(notificationChoices, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationChoice(@PathVariable int id) {
        notificationChoiceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
