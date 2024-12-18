package co.edu.unbosque.notificationback.util;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SendEmail {
    private static final String API_KEY = "a75655570780e1fbe080242f64c8e902-2ae9e1e8-4281-4fea-b7a8-9a86c6d656c8";
    private static final String BASE_URL = "https://ypvqgp.api.infobip.com/email/3/send";

    public static void sendEmail(String toEmail, String subject, String messageText) {
        RestTemplate restTemplate = new RestTemplate();

        // Encabezados de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "App " + API_KEY);
        headers.set("Content-Type", "multipart/form-data");
        headers.set("Accept", "application/json");

        // Cuerpo de la solicitud en formato multipart
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("from", "bc3fcbd1306857b05485b714c44c81248dc3@hotmail.com");
        body.add("subject", subject);
        body.add("to", String.format("{\"to\":\"%s\",\"placeholders\":{\"firstName\":\"Viajes-Global\"}}", toEmail));
        body.add("text", messageText);

        // Construye la solicitud
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Envía la solicitud
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, requestEntity, String.class);
            int statusCode = response.getStatusCodeValue();
            String responseString = response.getBody();
            if (statusCode == 200) {
                System.out.println("Email enviado exitosamente");
                System.out.println("Respuesta: " + responseString);
            } else {
                System.err.println("Error al enviar Email. Código: " + statusCode);
                System.err.println("Respuesta: " + responseString);
                if (statusCode == 400) {
                    System.err.println("Error de validación en la solicitud");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al enviar Email: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        sendEmail("maigueld29@gmail.com", "Prueba de Email", "Hi {{firstName}}, this is a test message from Infobip. Have a nice day!");
    }
}