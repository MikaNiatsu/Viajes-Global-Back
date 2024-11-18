package co.edu.unbosque.notificationback.controller;

import co.edu.unbosque.notificationback.model.NotificationChoiceDTO;
import co.edu.unbosque.notificationback.model.NotificationChoiceEntity;
import co.edu.unbosque.notificationback.service.DataMapper;
import co.edu.unbosque.notificationback.service.NotificationChoiceService;
import co.edu.unbosque.notificationback.util.SendEmail;
import co.edu.unbosque.notificationback.util.SendSMS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notification Controller", description = "Manage notification preferences and send notifications")
public class NotificationController {

    @Autowired
    private  NotificationChoiceService notificationChoiceService;

    @Operation(summary = "Get notification preferences by client ID", description = "Retrieves a client's notification preferences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification preferences found"),
            @ApiResponse(responseCode = "404", description = "Notification preferences not found")
    })
    @GetMapping("/client/{clientId}")
    public ResponseEntity<NotificationChoiceDTO> getNotificationChoiceByClientId(@PathVariable int clientId) {
        try {
            NotificationChoiceDTO preferences = notificationChoiceService.findById(clientId);
            return ResponseEntity.ok(preferences);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Create notification preferences", description = "Creates a new set of notification preferences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification preferences created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/createNotification")
    public ResponseEntity<NotificationChoiceDTO> createNotificationChoice(@RequestBody NotificationChoiceDTO notificationChoiceDTO) {
        try {
            NotificationChoiceDTO createdPreferences = notificationChoiceService.save(notificationChoiceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPreferences);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Update notification preferences", description = "Updates an existing set of notification preferences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification preferences updated"),
            @ApiResponse(responseCode = "400", description = "Error updating notification preferences")
    })
    @PostMapping("/update")
    public ResponseEntity<Boolean> updateNotification(@RequestBody NotificationChoiceDTO notificationChoiceDTO) {
        try {
            boolean isUpdated = notificationChoiceService.updateNotification(notificationChoiceDTO);
            return ResponseEntity.ok(isUpdated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @Operation(summary = "Get all notification preferences", description = "Retrieves all notification preferences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification preferences found"),
            @ApiResponse(responseCode = "404", description = "No notification preferences found")
    })
    @GetMapping("/showAll")
    public ResponseEntity<List<NotificationChoiceDTO>> getAllNotificationChoices() {
        List<NotificationChoiceDTO> preferences = notificationChoiceService.findAll();
        return preferences.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
                : ResponseEntity.ok(preferences);
    }

    @Operation(summary = "Delete notification preferences", description = "Deletes a set of notification preferences by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notification preferences deleted"),
            @ApiResponse(responseCode = "404", description = "Notification preferences not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationChoice(@PathVariable int id) {
        try {
            notificationChoiceService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Send a notification", description = "Sends a notification based on user preferences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification sent successfully"),
            @ApiResponse(responseCode = "400", description = "Error sending notification")
    })
    @PostMapping("/sendNotification")
    public ResponseEntity<String> sendNotification(@RequestBody Map<String, String> body) {
        try {
            int id = Integer.parseInt(body.get("id"));
            String messageHtml = body.get("messageHtml");
            String message = body.get("message");
            String to = body.get("to");
            String subject = body.get("subject");

            NotificationChoiceDTO preferences = notificationChoiceService.findById(id);
            NotificationChoiceEntity notificationChoice = DataMapper.notificationChoiceDTOtoEntity(preferences);

            if (notificationChoice.isEmail()) {
                SendEmail.sendEmail(to, subject, messageHtml);
            }
            if (notificationChoice.isSms()) {
                SendSMS.sendSms(to, message);
            }
            return ResponseEntity.ok("Notification sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error sending notification");
        }
    }
}