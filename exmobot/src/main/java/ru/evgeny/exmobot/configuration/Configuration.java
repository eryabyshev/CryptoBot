package ru.evgeny.exmobot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@org.springframework.context.annotation.Configuration
public class Configuration {

    private Environment env;

    @Autowired
    public Configuration(Environment environment) {
        env = environment;
    }

    public String getPathToLogFile() {
        return env.getProperty("path.to.log.file");
    }

    public String getPathToHistory() {
        return env.getProperty("path.to.history");
    }

    public Properties getSettingOfGmail() {
        Properties settings = new Properties();
        settings.put("gmail.sender.host", env.getProperty("gmail.sender.host"));
        settings.put("gmail.sender.port", env.getProperty("gmail.sender.port"));
        settings.put("gmail.transport.protocol", env.getProperty("gmail.transport.protocol"));
        settings.put("gmail.smtp.auth", env.getProperty("gmail.smtp.auth"));
        settings.put("gmail.smtp.starttls.enable", env.getProperty("gmail.smtp.starttls.enable"));
        settings.put("gmail.debug", env.getProperty("gmail.debug"));
        return settings;
    }
}
