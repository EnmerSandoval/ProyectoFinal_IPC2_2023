package com.example.backendguateempleos.model;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;


public class SendEmail {
    private String emailFrom = "valiti19@gmail.com";
    private String passwordFrom = "tolotvpxkwaahakl";

    private Properties mProperties;



    public SendEmail() {


    }

    public void sendEmailJakarta(String to, String subject, String content){

                Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", emailFrom);
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.auth", "true");

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, passwordFrom);
            }
        };
        Session session = Session.getInstance(props, authenticator);

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(to));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(to));
                    message.setSubject(subject);
                    message.setText(content);
                    Transport.send(message);
                    System.out.println("Email Message Sent Successfully");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }


    }
}