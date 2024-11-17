package co.edu.unbosque.notificationback.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class SendEmail {

    private final String smtpHost;
    private final int smtpPort;
    private final String username;
    private final String password;

    public SendEmail(String smtpHost, int smtpPort, String username, String password) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void sendEmailHTML(String to, String subject, File htmlFile) throws MessagingException, IOException {
        String content = new String(Files.readAllBytes(htmlFile.toPath()));
        sendEmail(to, subject, content);
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        Session session = createSession();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=utf-8");
        Transport.send(message);
        System.out.println("Email sent successfully to " + to);
    }

}
