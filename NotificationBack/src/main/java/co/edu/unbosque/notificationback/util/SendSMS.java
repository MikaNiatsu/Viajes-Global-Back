package co.edu.unbosque.notificationback.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SendSMS {

    private static String accountSid = "AC46906ed6338f9a20aa2847349cc9229c";
    private static String authToken = "d202a7c40359a05e5cc333a339c87850";
    private static String fromPhoneNumber = "ACA TOCA PONER EL NUMERO DE TELEFONO QUE ENVIARA EL MENSAJE, SI QUIERES PON EL TUYO";

    static {
        Twilio.init(accountSid, authToken);
    }

    public static void sendSms(String toPhoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(toPhoneNumber),
                    new com.twilio.type.PhoneNumber(fromPhoneNumber),
                    messageBody
            ).create();
            //Para enviar el sms tienes que poner esta linea de codigo SmsUtil.sendSms(Al numero que se va enviar, Mensaje a enviar);

            System.out.println("Mensaje enviado con SID: " + message.getSid());

        } catch (Exception e) {
            System.err.println("Error al enviar SMS: " + e.getMessage());
        }
    }
}
