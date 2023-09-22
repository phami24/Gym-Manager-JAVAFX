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
                + ".logo { text-align: center; margin-bottom: 20px; }"
                + ".logo img { max-width: 100%; height: auto; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<div class='header'>"
                + "<h2>Thank You for Joining Our Gym!</h2>"
                + "<div class='logo'>"
                + "<img src='https://img.freepik.com/vecteurs-premium/logo-musculation-fitness_7085-141.jpg?w=740' alt='Gym Logo'>"
                + "</div>"
                + "</div>"
                + "<div class='content'>"
                + "<p>We extend our warmest welcome to you, and we are genuinely excited to have you join our gym family." +
                " It is our sincere hope that your journey with us will be nothing short of extraordinary, filled with inspiring workouts, supportive friendships, and a healthier, happier you.</p>"
                + "</div>"
                + "<div class='footer'>"
                + "<p>Best regards,</p>"
                + "<p>Gym Center</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";


        sendEmailToMembers(memberEmails, subject, content);
    }


    public void sendEmailBirthDay(String recipientEmail, String subject, String message) {
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
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(username));
            emailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            emailMessage.setSubject(subject);

            // Create a multipart message to include both text and HTML content
            Multipart multipart = new MimeMultipart();

            // Create the text part of the message
            BodyPart textPart = new MimeBodyPart();
            textPart.setText(message);

            // Add the text part to the multipart message
            multipart.addBodyPart(textPart);

            // Set the content of the email message to be the multipart message
            emailMessage.setContent(multipart);

            // Send the email
            Transport.send(emailMessage);
            System.out.println("Birthday email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
