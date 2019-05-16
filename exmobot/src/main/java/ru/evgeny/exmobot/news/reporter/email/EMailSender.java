package ru.evgeny.exmobot.news.reporter.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import ru.evgeny.exmobot.news.reporter.IReporter;

@Controller
public class EMailSender implements IReporter {

    @Autowired
    private JavaMailSender mailSender;
    private String userName;


    public EMailSender setUserName(String userName) {
        this.userName = userName;
        ((JavaMailSenderImpl)mailSender).setUsername(userName);
        return this;
    }

    public EMailSender setPassword(String password) {
        ((JavaMailSenderImpl)mailSender).setPassword(password);
        return this;
    }


    @Override
    public String sendMessage(String... args) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userName);
        message.setSubject(args[0]);
        message.setText(args[1]);
        mailSender.send(message);
        return "OK";
    }
}
