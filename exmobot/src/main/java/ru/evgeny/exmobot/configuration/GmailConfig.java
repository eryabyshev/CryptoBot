package ru.evgeny.exmobot.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class GmailConfig {

    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;

    @Autowired
    public GmailConfig(LoadingSettingData loadingSettingData) {
        this.loadingSettingData = loadingSettingData;
    }

    private LoadingSettingData loadingSettingData;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties properties = loadingSettingData.getGmailSettings();

        mailSender.setHost(properties.getProperty("gmail.sender.host"));
        mailSender.setPort(Integer.valueOf(properties.getProperty("gmail.sender.port")));

        mailSender.setUsername(email);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.putAll(properties);

        return mailSender;
    }
}
