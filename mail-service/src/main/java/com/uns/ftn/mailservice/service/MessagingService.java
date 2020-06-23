package com.uns.ftn.mailservice.service;

import com.uns.ftn.mailservice.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MessagingService {

    private JavaMailSender javaMailSender;

    /*
     * Class for reading data from application.properties file
     */
    private Environment env;

    @Autowired
    public MessagingService(JavaMailSender javaMailSender, Environment env) {
        this.javaMailSender = javaMailSender;
        this.env = env;
    }

    /*
     * Logic for mail sending
     */
    @Async
    public void sendMail(MessageDTO mdto) {
        if (mdto.getIsAgentRegistration()) {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(env.getProperty("spring.mail.username"));
            mail.setFrom(env.getProperty("spring.mail.username"));
            mail.setSubject(mdto.getTitle());
            mail.setText(mdto.getContent());

            javaMailSender.send(mail);
        } else {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = "<p>Please click <a href=\"http://localhost:8090/registerUser/" + mdto.getToken() + "\">here</a> to verify your registration.</p>" +
                    "<br><br><p>RentaSoul Team</p>";

            try {
                helper.setText(htmlMsg, true); // Use this or above line.
                helper.setTo(env.getProperty("spring.mail.username"));
                helper.setSubject("Confirm your registration to RentaSoul services");
                helper.setFrom(env.getProperty("spring.mail.username"));
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

}
