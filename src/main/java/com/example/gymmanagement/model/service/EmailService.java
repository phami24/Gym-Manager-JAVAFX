package com.example.gymmanagement.model.service;

import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    private final String host = "smtp.gmail.com"; // Replace with your SMTP host
    private final String username = "buibinhminhpham2004@gmail.com"; // Replace with your SMTP username
    private final String password = "itaursywrqhhqvxs"; // Replace with your SMTP password

    public void sendEmailToMembers(List<String> memberEmails, String subject, String content) {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587"); // Replace with your SMTP port
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            for (String email : memberEmails) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            }
            message.setSubject(subject);
            message.setContent(content, "text/html");

            Transport.send(message);
            System.out.println("Emails sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendThankYouEmail(List<String> memberEmails) {
        String subject = "Thank You for Joining Our Gym";
        String content = "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; }"
                + ".header { background-color: #f2f2f2; padding: 20px; text-align: center; }"
                + ".content { padding: 20px; }"
                + ".footer { background-color: #f2f2f2; padding: 10px; text-align: center; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<div class='header'>"
                + "<h2>Thank You for Joining Our Gym!</h2>"
                + "</div>"
                + "<div class='content'>"
                + "<p>We are delighted to welcome you. We hope you will have a great experience at our gym.</p>"
                + "</div>"
                + "<div class='footer'>"
                + "<p>Best regards,</p>"
                + "<p>XYZ Gym</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        sendEmailToMembers(memberEmails, subject, content);
    }
}
