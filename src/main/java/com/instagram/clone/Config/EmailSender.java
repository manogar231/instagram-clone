package com.instagram.clone.Config;

import org.springframework.stereotype.Component;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Component
public class EmailSender {

    private String noReplyEmail = "anjanavenson1010@gmail.com";
    public void sendNoReplyEmail(JavaMailSender mailSender, String toAddress, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(noReplyEmail);
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
