package com.rajesh.UserManagment.services;

import com.rajesh.UserManagment.Entitys.User;
import com.rajesh.UserManagment.repositories.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String subject, String body, String to) {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    javaMailSender.send(message);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void  triggermail() throws MessagingException {
//        sendEmail("Password"," Password Use To Your Login Your Account.","rajeshpamuri@gmail.com",password);
//    }

}
