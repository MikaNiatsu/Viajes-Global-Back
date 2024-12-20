package co.edu.unbosque.notificationback.util;


import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class SendSMS {
    private static final String API_KEY = "a75655570780e1fbe080242f64c8e902-2ae9e1e8-4281-4fea-b7a8-9a86c6d656c8";
    private static final String BASE_URL = "https://sypvqgp.api.infobip.com/sms/2/text/advanced";

    public static void sendSms(String toPhoneNumber, String messageBody) {
        try {
            HttpResponse<JsonNode> response = Unirest.post(BASE_URL)
                    .header("Authorization", "App " + API_KEY)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .body(String.format("""
                            {
                                "messages": [{
                                    "destinations": [{
                                        "to": "%s"
                                    }],
                                    "from": "447491163443",
                                    "text": "%s"
                                }]
                            }
                            """, toPhoneNumber, messageBody))
                    .asJson();

            // Verificar la respuesta
            if (response.getStatus() == 200) {
                System.out.println("Mensaje enviado exitosamente");
                System.out.println("Respuesta: " + response.getBody().toString());
            } else {
                System.err.println("Error al enviar SMS. Código: " + response.getStatus());
                System.err.println("Respuesta: " + response.getBody().toString());
            }

        } catch (Exception e) {
            System.err.println("Error al enviar SMS: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        sendSms("573025346788", "Hello, this is a test message from Infobip. Have a nice day!");
    }

}
