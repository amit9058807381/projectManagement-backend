package com.amit.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl  implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendEmailWithToken(String userEmail, String link) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String subject="Join Project Team Invitation";
        String text =
                "<h3>Project Invitation</h3>" +
                        "<p>Click the button below to join the project:</p>" +
                        "<a href='" + link + "'>Join Project</a>";
        helper.setSubject(subject);
        helper.setText(text,true);
        helper.setTo(userEmail);

        try{
            javaMailSender.send(mimeMessage);
        } catch (MailException e) {
                throw new MailSendException("Failed to send email");
        }
    }
}
