package com.algaworks.algafood.domain.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    private JavaMailSender mailSender;

    private EmailProperties emailProperties;

    public SmtpEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties){
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
    }
    @Override
    public void enviar(Mensagem mensagem) {
        try{
            MimeMessage mimeMessage =mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(mensagem.getCorpo(), true);

            mailSender.send(mimeMessage);
        }catch(Exception e){
            throw new EmailException("Nao foi possível enviar email.");


        }

    }
}
