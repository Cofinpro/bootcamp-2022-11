package com.cofinprobootcamp.backend.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailSendService {

    private final JavaMailSender emailSender;

    public EmailSendService(JavaMailSender javaMailSender) {
        this.emailSender = javaMailSender;
    }
    public void sendProfileUpdateMail(String mailRecipientFullName, String changingUserEmailAddress, String mailRecipientEmailAddress) {
        String day = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String time = LocalTime.now().toString().substring(0, 5);

        String mailText = "Hallo " + mailRecipientFullName + ",\n\n" +
                "dein Profil in der Cofinpro Skill-DB wurde am " + day + " um " + time + " Uhr von " + changingUserEmailAddress + " geändert.";

        sendSimpleMessage(mailRecipientEmailAddress, "Profilupdate", mailText);
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mails.skilldb.cofinpro@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
