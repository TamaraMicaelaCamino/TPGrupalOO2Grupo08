package com.oo2.tpgrupalhito2grupo08.services.implementation;

import com.oo2.tpgrupalhito2grupo08.services.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService implements IEmailService {

    @Value("${spring.mail.username}")
    private String remitente;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }
    @Override
    public void enviarMail(String aUsuario, String asunto, String mensaje) {
        SimpleMailMessage mailMessage= new SimpleMailMessage();
        mailMessage.setFrom(remitente);
        mailMessage.setTo(aUsuario);
        mailMessage.setSubject(asunto);
        mailMessage.setText(mensaje);

        mailSender.send(mailMessage);
    }

    @Override
    public void enviarMailConArchico(String aUsuario, String asunto, String mensaje, File file) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage,true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(remitente);
            mimeMessageHelper.setTo(aUsuario);
            mimeMessageHelper.setSubject(asunto);
            mimeMessageHelper.setText(mensaje);
            mimeMessageHelper.addAttachment(file.getName(), file);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

