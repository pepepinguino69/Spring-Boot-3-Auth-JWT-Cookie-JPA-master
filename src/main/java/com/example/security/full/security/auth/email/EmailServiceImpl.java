package com.example.security.full.security.auth.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

// Annotation
@Service
// Class
// Implementing EmailService interface
public class EmailServiceImpl implements EmailService {

        private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

        @Autowired
        private JavaMailSender sender;

        @Override
        public String sendSimpleMail(EmailDetails emailDetails)  {
            LOGGER.info("EmailDetails: {}", emailDetails.toString());
            return sendEmailTool(emailDetails.getContent(),emailDetails.getEmail(), emailDetails.getSubject());
        }


        private String sendEmailTool(String textMessage, String email,String subject) {
            Boolean send = false;
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            try {
                helper.setTo(email);
                helper.setText(textMessage, true);
                helper.setSubject(subject);
                sender.send(message);
                send = true;
                LOGGER.info("Mail enviado!");
            } catch (MessagingException e) {
                LOGGER.error("Hubo un error al enviar el mail: {}", e);
            }
            if (send){return "OK";}
            return "ERROR";
        }



    }
